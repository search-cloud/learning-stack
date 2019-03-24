package io.vincent.compiler.c.parser;

import io.vincent.compiler.c.ast.Declarations;
import io.vincent.compiler.c.exception.CompileException;
import io.vincent.compiler.c.exception.FileException;
import io.vincent.compiler.c.exception.SemanticException;
import io.vincent.compiler.c.utils.ErrorHandler;

import java.io.File;
import java.util.*;

/**
 * import 文件包加载器
 *
 * @author Vincent
 */
public class LibraryLoader {
	private List<String> loadPath;
	private LinkedList<String> loadingLibraries;
	private Map<String, Declarations> loadedLibraries;

	/**
	 * 默认加载包的路径
	 *
	 * @return 返回查找路径列表
	 */
	private static List<String> defaultLoadPath() {
		List<String> paths = new ArrayList<>();
		paths.add(".");
		paths.add("./import");
		paths.add("../import");
		paths.add("/import");
		paths.add("learning-compilation/import");
		return paths;
	}

	public LibraryLoader() {
		this(defaultLoadPath());
	}

	private LibraryLoader(List<String> loadPath) {
		this.loadPath = loadPath;
		this.loadingLibraries = new LinkedList<>();
		this.loadedLibraries = new HashMap<>();
	}

	public void addLoadPath(String path) {
		loadPath.add(path);
	}

	Declarations loadLibrary(String libId, ErrorHandler handler) throws CompileException {
		if (loadingLibraries.contains(libId)) {
			throw new SemanticException("recursive import from " + loadingLibraries.getLast() + ": " + libId);
		}
		loadingLibraries.addLast(libId);   // stop recursive import
		Declarations declarations = loadedLibraries.get(libId);
		if (declarations != null) {
			// Already loaded import file.  Returns cached declarations.
			return declarations;
		}
		declarations = Parser.parseDeclFile(searchLibrary(libId), this, handler);
		loadedLibraries.put(libId, declarations);
		loadingLibraries.removeLast();
		return declarations;
	}

	private File searchLibrary(String libId) throws FileException {
		try {
			for (String path : loadPath) {
				File file = new File(path + "/" + libPath(libId) + ".hb");
				if (file.exists()) {
					return file;
				}
			}
			throw new FileException("no such library header file: " + libId);
		} catch (SecurityException ex) {
			throw new FileException(ex.getMessage());
		}
	}

	private String libPath(String id) {
		return id.replace('.', '/');
	}
}
