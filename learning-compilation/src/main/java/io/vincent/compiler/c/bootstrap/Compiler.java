package io.vincent.compiler.c.bootstrap;


import io.vincent.compiler.c.ast.AST;
import io.vincent.compiler.c.ast.ExpressionNode;
import io.vincent.compiler.c.ast.StatementNode;
import io.vincent.compiler.c.config.CompilerMode;
import io.vincent.compiler.c.config.Options;
import io.vincent.compiler.c.config.SourceFile;
import io.vincent.compiler.c.core.IRGenerator;
import io.vincent.compiler.c.exception.*;
import io.vincent.compiler.c.ir.IR;
import io.vincent.compiler.c.parser.Parser;
import io.vincent.compiler.c.sysdep.AssemblyCode;
import io.vincent.compiler.c.type.TypeTable;
import io.vincent.compiler.c.utils.ErrorHandler;

import java.io.*;
import java.util.List;

/**
 * 编译器主入口
 *
 * @author Asion
 */
public class Compiler {

	/**
	 * bootstrap
	 *
	 * @param args command parameters
	 */
	static public void main(String[] args) {
		new Compiler(Options.ProgramName).commandMain(args);
	}

	private final ErrorHandler errorHandler;

	private Compiler(String programName) {
		this.errorHandler = new ErrorHandler(programName);
	}

	/**
	 * 处理参数，检查语法，构建，编译src
	 *
	 * @param args 参数
	 */
	private void commandMain(String[] args) {
		Options opts = parseOptions(args);
		if (opts == null || opts.mode() == CompilerMode.CheckSyntax) {
			System.exit(checkSyntax(opts) ? 0 : 1);
		}
		try {
			List<SourceFile> sourceFiles = opts.sourceFiles();
			// 开始构建
			build(sourceFiles, opts);
			System.exit(0);
		} catch (CompileException ex) {
			errorHandler.error(ex.getMessage());
			System.exit(1);
		}
	}

	/**
	 * 处理参数
	 *
	 * @param args 参数
	 * @return 配置选项
	 * @see Options#parse(String[])
	 */
	private Options parseOptions(String[] args) {
		try {
			return Options.parse(args);
		} catch (OptionParseError err) {
			errorHandler.error(err.getMessage());
			errorHandler.error("Try \"cbc --help\" for usage");
			System.exit(1);
			return null;   // never reach
		}
	}

	/**
	 * 语法校验
	 *
	 * @param options 选项
	 * @return 语法是否正确
	 */
	private boolean checkSyntax(Options options) {
		boolean failed = false;
		if (options == null) return false;
		List<SourceFile> sourceFiles = options.sourceFiles();
		for (SourceFile src : sourceFiles) {
			if (isValidSyntax(src.path(), options)) {
				System.out.println(src.path() + ": Syntax OK");
			} else {
				System.out.println(src.path() + ": Syntax Error");
				failed = true;
			}
		}
		return !failed;
	}

	/**
	 * 语法校验
	 *
	 * @param path    文件路径
	 * @param options 选项
	 * @return 语法是否正确
	 */
	private boolean isValidSyntax(String path, Options options) {
		try {
			parseFile(path, options);
			return true;
		} catch (SyntaxException ex) {
			return false;
		} catch (FileException ex) {
			errorHandler.error(ex.getMessage());
			return false;
		}
	}

	/**
	 * 源码构建
	 *
	 * @param sourceFiles    源文件
	 * @param options 配置选项
	 * @throws CompileException CompileException
	 */
	private void build(List<SourceFile> sourceFiles, Options options) throws CompileException {
		for (SourceFile src : sourceFiles) {
			// 编译源代码
			if (src.isCflatSource()) {
				String destPath = options.asmFileNameOf(src);
				compile(src.path(), destPath, options);
				src.setCurrentName(destPath);
			}

			// 汇编成目标代码
			if (!options.isAssembleRequired()) continue;
			if (src.isAssemblySource()) {
				String destPath = options.objFileNameOf(src);
				assemble(src.path(), destPath, options);
				src.setCurrentName(destPath);
			}
		}
		if (!options.isLinkRequired()) {
			return;
		}
		// 链接目标代码
		link(options);
	}

	/**
	 * 编译代码
	 *
	 * @param srcPath  源文件路径
	 * @param destPath 目标文件路径
	 * @param options  配置选项
	 * @throws CompileException 编译异常
	 */
	private void compile(String srcPath, String destPath, Options options) throws CompileException {

		//------------------编译器前端 start---------------------
		// 1. 解析源代码 生成抽象语法树（AST）
		AST ast = parseFile(srcPath, options);
		if (dumpAST(ast, options.mode())) {
			return;
		}

		// 类型表
		TypeTable types = options.typeTable();

		// 2. 对抽象语法树进行语义分析
		AST sem = semanticAnalyze(ast, types, options);
		if (dumpSemant(sem, options.mode())) {
			return;
		}

		// 3. 生成中间代码
		IR ir = new IRGenerator(types, errorHandler).generate(sem);
		if (dumpIR(ir, options.mode())) {
			return;
		}
		//------------------编译器前端 end---------------------

		// 4. 生成汇编代码
		AssemblyCode asm = generateAssembly(ir, options);
		if (dumpAsm(asm, options.mode())) {
			return;
		}
		if (printAsm(asm, options.mode())) {
			return;
		}
		writeFile(destPath, asm.toSource());
	}

	/**
	 * 语法分析，生成抽象语法树🌲
	 *
	 * @param path    源文件路径
	 * @param options 配置选项
	 * @return 抽象语法树🌲
	 * @throws SyntaxException 语法异常
	 * @throws FileException   文件异常
	 */
	private AST parseFile(String path, Options options) throws SyntaxException, FileException {
		return Parser.parseFile(new File(path), options.loader(), errorHandler, options.doesDebugParser());
	}

	/**
	 * 对抽象语法树🌲进行语义分析
	 *
	 * @param ast     法抽象树🌲
	 * @param types   类型表
	 * @param options 配置选项
	 * @return 抽象语法树🌲
	 * @throws SemanticException 语义异常
	 */
	private AST semanticAnalyze(AST ast, TypeTable types, Options options) throws SemanticException {
		new LocalResolver(errorHandler).resolve(ast);
		new TypeResolver(types, errorHandler).resolve(ast);
		types.semanticCheck(errorHandler);
		if (options.mode() == CompilerMode.DumpReference) {
			ast.dump();
			return ast;
		}
		new DereferenceChecker(types, errorHandler).check(ast);
		new TypeChecker(types, errorHandler).check(ast);
		return ast;
	}

	/**
	 * 生成汇编代码
	 *
	 * @param ir      中间代码生成器
	 * @param options 配置选项
	 * @return 汇编代码
	 */
	private AssemblyCode generateAssembly(IR ir, Options options) {
		return options.codeGenerator(errorHandler).generate(ir);
	}

	/**
	 * 汇编成目标代码
	 *
	 * @param srcPath  源文件目录
	 * @param destPath 目标目录
	 * @param options  配置选项
	 * @throws IPCException IPCException
	 */
	private void assemble(String srcPath, String destPath, Options options) throws IPCException {
		options.assembler(errorHandler).assemble(srcPath, destPath, options.assembilerOptions());
	}

	/**
	 * 链接目标代码
	 *
	 * @param options 配置选项
	 * @throws IPCException IPCException
	 */
	private void link(Options options) throws IPCException {
		if (!options.isGeneratingSharedLibrary()) {
			generateExecutable(options);
		} else {
			generateSharedLibrary(options);
		}
	}

	/**
	 * 生成可执行文件
	 *
	 * @param opts 配置选项
	 * @throws IPCException IPC异常
	 */
	private void generateExecutable(Options opts) throws IPCException {
		opts.linker(errorHandler).generateExecutable(opts.ldArgs(), opts.exeFileName(), opts.ldOptions());
	}

	/**
	 * 生成库文件
	 *
	 * @param opts 配置选项
	 * @throws IPCException IPC异常
	 */
	private void generateSharedLibrary(Options opts) throws IPCException {
		opts.linker(errorHandler).generateSharedLibrary(opts.ldArgs(), opts.soFileName(), opts.ldOptions());
	}

	/**
	 * 输出字符串内容到文件
	 *
	 * @param path 文件路径
	 * @param str  内容
	 * @throws FileException 文件异常
	 */
	private void writeFile(String path, String str) throws FileException {
		if (path.equals("-")) {
			System.out.print(str);
			return;
		}
		try (BufferedWriter f = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))) {
			f.write(str);
		} catch (FileNotFoundException ex) {
			errorHandler.error("file not found: " + path);
			throw new FileException("file error");
		} catch (IOException ex) {
			errorHandler.error("IO error" + ex.getMessage());
			throw new FileException("file error");
		}
	}

	/**
	 * 根据配置参数，dump 不同阶段的输出
	 *
	 * @param ast 抽象语法树
	 * @param mode 模式
	 * @return 成功与否
	 */
	private boolean dumpAST(AST ast, CompilerMode mode) {
		switch (mode) {
			case DumpTokens:
				ast.dumpTokens(System.out);
				return true;
			case DumpAST:
				ast.dump();
				return true;
			case DumpStmt:
				findStmt(ast).dump();
				return true;
			case DumpExpr:
				findExpr(ast).dump();
				return true;
			default:
				return false;
		}
	}

	private StatementNode findStmt(AST ast) {
		StatementNode stmt = ast.getSingleMainStmt();
		if (stmt == null) {
			errorExit("source file does not contains main()");
		}
		return stmt;
	}

	private ExpressionNode findExpr(AST ast) {
		ExpressionNode expr = ast.getSingleMainExpr();
		if (expr == null) {
			errorExit("source file does not contains single expression");
		}
		return expr;
	}

	private boolean dumpSemant(AST ast, CompilerMode mode) {
		switch (mode) {
			case DumpReference:
				return true;
			case DumpSemantic:
				ast.dump();
				return true;
			default:
				return false;
		}
	}

	private boolean dumpIR(IR ir, CompilerMode mode) {
		if (mode == CompilerMode.DumpIR) {
			ir.dump();
			return true;
		} else {
			return false;
		}
	}

	private boolean dumpAsm(AssemblyCode asm, CompilerMode mode) {
		if (mode == CompilerMode.DumpAsm) {
			asm.dump(System.out);
			return true;
		} else {
			return false;
		}
	}

	private boolean printAsm(AssemblyCode asm, CompilerMode mode) {
		if (mode == CompilerMode.PrintAsm) {
			System.out.print(asm.toSource());
			return true;
		} else {
			return false;
		}
	}

	private void errorExit(String msg) {
		errorHandler.error(msg);
		System.exit(1);
	}
}
