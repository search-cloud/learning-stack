package io.vincent.compiler.c.core;

import io.vincent.compiler.c.asm.Label;
import io.vincent.compiler.c.ast.*;
import io.vincent.compiler.c.entity.DefinedFunction;
import io.vincent.compiler.c.entity.DefinedVariable;
import io.vincent.compiler.c.entity.Entity;
import io.vincent.compiler.c.entity.LocalScope;
import io.vincent.compiler.c.exception.JumpError;
import io.vincent.compiler.c.exception.SemanticException;
import io.vincent.compiler.c.ir.*;
import io.vincent.compiler.c.type.Type;
import io.vincent.compiler.c.type.TypeTable;
import io.vincent.compiler.c.utils.ErrorHandler;
import io.vincent.compiler.c.utils.ListUtils;

import java.util.*;

/**
 * IR Generator.
 *
 * @author Vincent
 */
public class IRGenerator implements ASTVisitor<Void, Expression> {

	private final TypeTable typeTable;
	private final ErrorHandler errorHandler;

	public IRGenerator(TypeTable typeTable, ErrorHandler errorHandler) {
		this.typeTable = typeTable;
		this.errorHandler = errorHandler;
	}

	/**
	 * Begins to generate IR.
	 *
	 * @param ast abstract syntax tree.
	 * @return IR intermediate representation.
	 * @throws SemanticException semantic exception.
	 */
	public IR generate(AST ast) throws SemanticException {

		for (DefinedVariable var : ast.definedVariables()) {
			if (var.hasInitializer()) {
				var.setIR(transformExpr(var.initializer()));
			}
		}
		for (DefinedFunction fun : ast.definedFunctions()) {
			fun.setIR(compileFunctionBody(fun));
		}
		if (errorHandler.errorOccurred()) {
			throw new SemanticException("IR generation failed.");
		}

		return ast.ir();
	}

	//
	// Definitions --------------------------------------------------------------------------------------
	//

	// All statements with IR
	private List<Statement> statements;
	// scope stack
	private LinkedList<LocalScope> scopeStack;
	// break stack
	private LinkedList<Label> breakStack;
	// continue stack
	private LinkedList<Label> continueStack;
	// jump table
	private Map<String, JumpEntry> jumpMap;

	/**
	 * compile the body of a function.
	 *
	 * @param function function.
	 * @return statements with IR.
	 */
	public List<Statement> compileFunctionBody(DefinedFunction function) {
		statements = new ArrayList<>();
		scopeStack = new LinkedList<>();
		breakStack = new LinkedList<>();
		continueStack = new LinkedList<>();
		jumpMap = new HashMap<>();

		// Transform statement by visitor pattern.
		transformStmt(function.body());
		// check jump links.
		checkJumpLinks(jumpMap);

		return statements;
	}

	private void transformStmt(StatementNode node) {
		node.accept(this);
	}

	private void transformStmt(ExpressionNode node) {
		node.accept(this);
	}

	private int exprNestLevel = 0;

	private Expression transformExpr(ExpressionNode node) {
		exprNestLevel++;
		Expression e = node.accept(this);
		exprNestLevel--;
		return e;
	}

	private boolean isStatement() {
		return (exprNestLevel == 0);
	}

	private void assign(Location loc, Expression lhs, Expression rhs) {
		statements.add(new Assign(loc, addressOf(lhs), rhs));
	}

	private DefinedVariable tmpVar(Type t) {
		return scopeStack.getLast().allocateTmp(t);
	}

	private void label(Label label) {
		statements.add(new LabelStatement(null, label));
	}

	private void jump(Location loc, Label target) {
		statements.add(new Jump(loc, target));
	}

	private void jump(Label target) {
		jump(null, target);
	}

	/**
	 * Conditional jump.
	 *
	 * @param location  source location.
	 * @param condition condition of the jump expression.
	 * @param thenLabel then target.
	 * @param elseLabel else target.
	 */
	private void cjump(Location location, Expression condition, Label thenLabel, Label elseLabel) {
		statements.add(new CJump(location, condition, thenLabel, elseLabel));
	}

	private void pushBreak(Label label) {
		breakStack.add(label);
	}

	private void popBreak() {
		if (breakStack.isEmpty()) {
			throw new Error("unmatched push/pop for break stack");
		}
		breakStack.removeLast();
	}

	private Label currentBreakTarget() {
		if (breakStack.isEmpty()) {
			throw new JumpError("break from out of loop");
		}
		return breakStack.getLast();
	}

	private void pushContinue(Label label) {
		continueStack.add(label);
	}

	private void popContinue() {
		if (continueStack.isEmpty()) {
			throw new Error("unmatched push/pop for continue stack");
		}
		continueStack.removeLast();
	}

	private Label currentContinueTarget() {
		if (continueStack.isEmpty()) {
			throw new JumpError("continue from out of loop");
		}
		return continueStack.getLast();
	}

	//
	// Statements -----------------------------------------------------------------------------------------
	//

	public Void visit(BlockNode node) {
		scopeStack.add(node.scope());
		for (DefinedVariable var : node.variables()) {
			if (var.hasInitializer()) {
				if (var.isPrivate()) {
					// static variables
					var.setIR(transformExpr(var.initializer()));
				} else {
					assign(var.location(),
							ref(var), transformExpr(var.initializer()));
				}
			}
		}
		for (StatementNode s : node.stmts()) {
			transformStmt(s);
		}
		scopeStack.removeLast();
		return null;
	}

	public Void visit(ExpressionStatementNode node) {
		// do not use transformStmt here, to receive compiled tree.
		Expression e = node.expr().accept(this);
		if (e != null) {
			//stmts.add(new ExprStmt(node.expression().location(), e));
			errorHandler.warn(node.location(), "useless expression");
		}
		return null;
	}

	/**
	 * Transform if tree node into IR.
	 *
	 * @param node if node.
	 * @return statement IR.
	 */
	public Void visit(IfNode node) {
		Label thenLabel = new Label();
		Label elseLabel = new Label();
		Label endLabel = new Label();

		Expression cond = transformExpr(node.cond());

		if (node.elseBody() == null) {
			cjump(node.location(), cond, thenLabel, endLabel);
			label(thenLabel);
			// Transform if body
			transformStmt(node.thenBody());
			label(endLabel);
		} else {
			cjump(node.location(), cond, thenLabel, elseLabel);
			label(thenLabel);
			// Transform if body
			transformStmt(node.thenBody());
			jump(endLabel);
			label(elseLabel);
			// Transform else body
			transformStmt(node.elseBody());
			label(endLabel);
		}

		return null;
	}

	public Void visit(SwitchNode node) {
		List<Case> cases = new ArrayList<>();
		Label endLabel = new Label();
		Label defaultLabel = endLabel;

		Expression cond = transformExpr(node.cond());
		for (CaseNode c : node.cases()) {
			if (c.isDefault()) {
				defaultLabel = c.label();
			} else {
				for (ExpressionNode val : c.values()) {
					Expression v = transformExpr(val);
					cases.add(new Case(((Int) v).value(), c.label()));
				}
			}
		}
		statements.add(new Switch(node.location(), cond, cases, defaultLabel, endLabel));
		pushBreak(endLabel);
		for (CaseNode c : node.cases()) {
			label(c.label());
			transformStmt(c.body());
		}
		popBreak();
		label(endLabel);
		return null;
	}

	public Void visit(CaseNode node) {
		throw new Error("must not happen");
	}

	/**
	 * Transform while node into IR.
	 *
	 * @param node while node.
	 * @return while statement IR.
	 */
	public Void visit(WhileNode node) {
		Label begLabel = new Label();
		Label bodyLabel = new Label();
		Label endLabel = new Label();

		label(begLabel);
		cjump(node.location(), transformExpr(node.cond()), bodyLabel, endLabel);
		label(bodyLabel);
		pushContinue(begLabel);
		pushBreak(endLabel);
		transformStmt(node.body());
		popBreak();
		popContinue();
		jump(begLabel);
		label(endLabel);
		return null;
	}

	/**
	 * Transform do while node into IR.
	 *
	 * @param node do while node.
	 * @return do while statement IR.
	 */
	public Void visit(DoWhileNode node) {
		Label begLabel = new Label();
		Label contLabel = new Label();  // before condition (end of body)
		Label endLabel = new Label();

		pushContinue(contLabel);
		pushBreak(endLabel);
		label(begLabel);
		transformStmt(node.body());
		popBreak();
		popContinue();
		label(contLabel);
		cjump(node.location(), transformExpr(node.cond()), begLabel, endLabel);
		label(endLabel);
		return null;
	}

	public Void visit(ForNode node) {
		Label begLabel = new Label();
		Label bodyLabel = new Label();
		Label contLabel = new Label();
		Label endLabel = new Label();
		if (node.init() != null) transformStmt(node.init());
		label(begLabel);
		cjump(node.location(),
				transformExpr(node.cond()), bodyLabel, endLabel);
		label(bodyLabel);
		pushContinue(contLabel);
		pushBreak(endLabel);
		transformStmt(node.body());
		popBreak();
		popContinue();
		label(contLabel);
		if (node.incr() != null) transformStmt(node.incr());
		jump(begLabel);
		label(endLabel);
		return null;
	}

	public Void visit(BreakNode node) {
		try {
			jump(node.location(), currentBreakTarget());
		} catch (JumpError err) {
			error(node, err.getMessage());
		}
		return null;
	}

	public Void visit(ContinueNode node) {
		try {
			jump(node.location(), currentContinueTarget());
		} catch (JumpError err) {
			error(node, err.getMessage());
		}
		return null;
	}

	public Void visit(LabelNode node) {
		try {
			statements.add(new LabelStatement(node.location(),
					defineLabel(node.name(), node.location())));
			if (node.stmt() != null) {
				transformStmt(node.stmt());
			}
		} catch (SemanticException ex) {
			error(node, ex.getMessage());
		}
		return null;
	}

	public Void visit(GotoNode node) {
		jump(node.location(), referLabel(node.target()));
		return null;
	}

	public Void visit(ReturnNode node) {
		statements.add(new Return(node.location(),
				node.expr() == null ? null : transformExpr(node.expr())));
		return null;
	}

	class JumpEntry {
		public Label label;
		public long namReference;
		public boolean defined;
		public Location location;

		public JumpEntry(Label label) {
			this.label = label;
			namReference = 0;
			defined = false;
		}
	}

	private Label defineLabel(String name, Location loc) throws SemanticException {
		JumpEntry ent = getJumpEntry(name);
		if (ent.defined) {
			throw new SemanticException(
					"duplicated jump labels in " + name + "(): " + name);
		}
		ent.defined = true;
		ent.location = loc;
		return ent.label;
	}

	private Label referLabel(String name) {
		JumpEntry ent = getJumpEntry(name);
		ent.namReference++;
		return ent.label;
	}

	private JumpEntry getJumpEntry(String name) {
		JumpEntry ent = jumpMap.get(name);
		if (ent == null) {
			ent = new JumpEntry(new Label());
			jumpMap.put(name, ent);
		}
		return ent;
	}

	private void checkJumpLinks(Map<String, JumpEntry> jumpMap) {
		for (Map.Entry<String, JumpEntry> ent : jumpMap.entrySet()) {
			String labelName = ent.getKey();
			JumpEntry jump = ent.getValue();
			if (!jump.defined) {
				errorHandler.error(jump.location,
						"undefined label: " + labelName);
			}
			if (jump.namReference == 0) {
				errorHandler.warn(jump.location,
						"useless label: " + labelName);
			}
		}
	}

	//
	// Expressions (with branches) ---------------------------------------------------------------------------
	//

	public Expression visit(CondExpressionNode node) {
		Label thenLabel = new Label();
		Label elseLabel = new Label();
		Label endLabel = new Label();
		DefinedVariable var = tmpVar(node.type());

		Expression cond = transformExpr(node.cond());
		cjump(node.location(), cond, thenLabel, elseLabel);
		label(thenLabel);
		assign(node.thenExpr().location(),
				ref(var), transformExpr(node.thenExpr()));
		jump(endLabel);
		label(elseLabel);
		assign(node.elseExpr().location(),
				ref(var), transformExpr(node.elseExpr()));
		jump(endLabel);
		label(endLabel);
		return isStatement() ? null : ref(var);
	}

	public Expression visit(LogicalAndNode node) {
		Label rightLabel = new Label();
		Label endLabel = new Label();
		DefinedVariable var = tmpVar(node.type());

		assign(node.left().location(), ref(var), transformExpr(node.left()));
		cjump(node.location(), ref(var), rightLabel, endLabel);
		label(rightLabel);
		assign(node.right().location(), ref(var), transformExpr(node.right()));
		label(endLabel);
		return isStatement() ? null : ref(var);
	}

	public Expression visit(LogicalOrNode node) {
		Label rightLabel = new Label();
		Label endLabel = new Label();
		DefinedVariable var = tmpVar(node.type());

		assign(node.left().location(), ref(var), transformExpr(node.left()));
		cjump(node.location(), ref(var), endLabel, rightLabel);
		label(rightLabel);
		assign(node.right().location(), ref(var), transformExpr(node.right()));
		label(endLabel);
		return isStatement() ? null : ref(var);
	}

	//
	// Expressions (with side effects) -----------------------------------------------------------------------
	//

	/**
	 * Visit assign expression.
	 *
	 * @param node assign node.
	 * @return expression.
	 */
	public Expression visit(AssignNode node) {
		Location lLoc = node.lhs().location();
		Location rLoc = node.rhs().location();
		if (isStatement()) {
			// Evaluate RHS before LHS.
			// #@@range/Assign_stmt{
			Expression rhs = transformExpr(node.rhs());
			assign(lLoc, transformExpr(node.lhs()), rhs);
			// #@@}
			return null;
		} else {
			// lhs = rhs -> tmp = rhs, lhs = tmp, tmp
			// #@@range/Assign_expr{
			DefinedVariable tmp = tmpVar(node.rhs().type());
			assign(rLoc, ref(tmp), transformExpr(node.rhs()));
			assign(lLoc, transformExpr(node.lhs()), ref(tmp));
			return ref(tmp);
			// #@@}
		}
	}

	/**
	 * Visit the operator assign node
	 *
	 * @param node assign node.
	 * @return expression.
	 */
	public Expression visit(OpAssignNode node) {
		// Evaluate RHS before LHS.
		Expression rhs = transformExpr(node.rhs());
		Expression lhs = transformExpr(node.lhs());
		Type t = node.lhs().type();
		Operator operator = Operator.internBinary(node.operator(), t.isSigned());
		return transformOpAssign(node.location(), operator, t, lhs, rhs);
	}

	public Expression visit(PrefixOpNode node) {
		// ++expression -> expression += 1
		Type t = node.expr().type();
		return transformOpAssign(node.location(), binOp(node.operator()), t, transformExpr(node.expr()), imm(t, 1));
	}

	// #@@range/SuffixOp{
	public Expression visit(SuffixOpNode node) {
		// #@@range/SuffixOp_init{
		Expression expression = transformExpr(node.expr());
		Type t = node.expr().type();
		Operator operator = binOp(node.operator());
		Location loc = node.location();
		// #@@}

		if (isStatement()) {
			// expression++; -> expression += 1;
			transformOpAssign(loc, operator, t, expression, imm(t, 1));
			return null;
		} else if (expression.isVar()) {
			// cont(expression++) -> v = expression; expression = v + 1; cont(v)
			DefinedVariable v = tmpVar(t);
			assign(loc, ref(v), expression);
			assign(loc, expression, bin(operator, t, ref(v), imm(t, 1)));
			return ref(v);
		} else {
			// cont(expression++) -> a = &expression; v = *a; *a = *a + 1; cont(v)
			// #@@range/SuffixOp_expr{
			DefinedVariable a = tmpVar(pointerTo(t));
			DefinedVariable v = tmpVar(t);
			assign(loc, ref(a), addressOf(expression));
			assign(loc, ref(v), mem(a));
			assign(loc, mem(a), bin(operator, t, mem(a), imm(t, 1)));
			return ref(v);
			// #@@}
		}
	}
	// #@@}

	/**
	 * Transform operator assign node.
	 *
	 * @param location location of the source.
	 * @param operator operator.
	 * @param lhsType  the type of the left expression.
	 * @param lhs      the left expression.
	 * @param rhs      the right expression.
	 * @return expression.
	 */
	private Expression transformOpAssign(Location location, Operator operator, Type lhsType, Expression lhs, Expression rhs) {
		if (lhs.isVar()) {
			// cont(lhs += rhs) -> lhs = lhs + rhs; cont(lhs)
			assign(location, lhs, bin(operator, lhsType, lhs, rhs));
			return isStatement() ? null : lhs;
		} else {
			// cont(lhs += rhs) -> a = &lhs; *a = *a + rhs; cont(*a)
			DefinedVariable a = tmpVar(pointerTo(lhsType));
			assign(location, ref(a), addressOf(lhs));
			assign(location, mem(a), bin(operator, lhsType, mem(a), rhs));
			return isStatement() ? null : mem(a);
		}
	}
	// #@@}

	// #@@range/bin{
	private Binary bin(Operator operator, Type leftType, Expression left, Expression right) {
		if (isPointerArithmetic(operator, leftType)) {
			return new Binary(left.type(), operator, left,
					new Binary(right.type(), Operator.MUL,
							right, ptrBaseSize(leftType)));
		} else {
			return new Binary(left.type(), operator, left, right);
		}
	}
	// #@@}

	// #@@range/Funcall{
	public Expression visit(FuncallNode node) {
		List<Expression> args = new ArrayList<Expression>();
		for (ExpressionNode arg : ListUtils.reverse(node.args())) {
			args.add(0, transformExpr(arg));
		}
		Expression call = new Call(asmType(node.type()),
				transformExpr(node.expr()), args);
		if (isStatement()) {
			statements.add(new ExpressionStatement(node.location(), call));
			return null;
		} else {
			DefinedVariable tmp = tmpVar(node.type());
			assign(node.location(), ref(tmp), call);
			return ref(tmp);
		}
	}
	// #@@}

	//
	// Expressions (no side effects) --------------------------------------------------------------------------
	//

	/**
	 * Transform Binary operation node.
	 *
	 * @param node Binary operation node.
	 * @return expression.
	 */
	public Expression visit(BinaryOpNode node) {

		Expression right = transformExpr(node.right());
		Expression left = transformExpr(node.left());
		// get semantic operator.
		Operator operator = Operator.internBinary(node.operator(), node.type().isSigned());
		Type t = node.type();

		Type r = node.right().type();
		Type l = node.left().type();

		// duel with pointer type.
		if (isPointerDiff(operator, l, r)) {
			// ptr - ptr -> (ptr - ptr) / ptrBaseSize
			Expression tmp = new Binary(asmType(t), operator, left, right);
			return new Binary(asmType(t), Operator.S_DIV, tmp, ptrBaseSize(l));
		} else if (isPointerArithmetic(operator, l)) {
			// ptr + int -> ptr + (int * ptrBaseSize)
			return new Binary(asmType(t), operator,
					left,
					new Binary(asmType(r), Operator.MUL, right, ptrBaseSize(l)));
		} else if (isPointerArithmetic(operator, r)) {
			// int + ptr -> (int * ptrBaseSize) + ptr
			return new Binary(asmType(t), operator,
					new Binary(asmType(l), Operator.MUL, left, ptrBaseSize(r)),
					right);
		} else {
			// int + int
			// #@@range/BinaryOp_int{
			return new Binary(asmType(t), operator, left, right);
			// #@@}
		}
	}

	/**
	 * Transform Unary operation node.
	 *
	 * @param node Unary operation node.
	 * @return expression.
	 */
	public Expression visit(UnaryOpNode node) {
		if (node.operator().equals("+")) {
			// +expression -> expression
			return transformExpr(node.expr());
		} else {
			return new Unary(asmType(node.type()), Operator.internUnary(node.operator()), transformExpr(node.expr()));
		}
	}

	// #@@range/Aref{
	public Expression visit(ArefNode node) {
		Expression expression = transformExpr(node.baseExpr());
		Expression offset = new Binary(ptrdiff_t(), Operator.MUL,
				size(node.elementSize()), transformIndex(node));
		Binary addr = new Binary(ptr_t(), Operator.ADD, expression, offset);
		return mem(addr, node.type());
	}
	// #@@}

	// For multidimension array: t[e][d][c][b][a] ary;
	// &ary[a0][b0][c0][d0][e0]
	//     = &ary + edcb*a0 + edc*b0 + ed*c0 + e*d0 + e0
	//     = &ary + (((((a0)*b + b0)*c + c0)*d + d0)*e + e0) * sizeof(t)
	//
	private Expression transformIndex(ArefNode node) {
		if (node.isMultiDimension()) {
			return new Binary(int_t(), Operator.ADD,
					transformExpr(node.index()),
					new Binary(int_t(), Operator.MUL,
							new Int(int_t(), node.length()),
							transformIndex((ArefNode) node.expr())));
		} else {
			return transformExpr(node.index());
		}
	}

	// #@@range/Member{
	public Expression visit(MemberNode node) {
		Expression expression = addressOf(transformExpr(node.expr()));
		Expression offset = ptrdiff(node.offset());
		Expression addr = new Binary(ptr_t(), Operator.ADD, expression, offset);
		// #@@range/Member_ret{
		return node.isLoadable() ? mem(addr, node.type()) : addr;
		// #@@}
	}
	// #@@}

	// #@@range/PtrMember{
	public Expression visit(PtrMemberNode node) {
		Expression expression = transformExpr(node.expr());
		Expression offset = ptrdiff(node.offset());
		Expression addr = new Binary(ptr_t(), Operator.ADD, expression, offset);
		return node.isLoadable() ? mem(addr, node.type()) : addr;
	}
	// #@@}

	// #@@range/Dereference{
	public Expression visit(DereferenceNode node) {
		Expression addr = transformExpr(node.expr());
		return node.isLoadable() ? mem(addr, node.type()) : addr;
	}
	// #@@}

	public Expression visit(AddressNode node) {
		Expression e = transformExpr(node.expr());
		return node.expr().isLoadable() ? addressOf(e) : e;
	}

	public Expression visit(CastNode node) {
		if (node.isEffectiveCast()) {
			return new Unary(asmType(node.type()),
					node.expr().type().isSigned() ? Operator.S_CAST : Operator.U_CAST,
					transformExpr(node.expr()));
		} else if (isStatement()) {
			transformStmt(node.expr());
			return null;
		} else {
			return transformExpr(node.expr());
		}
	}

	public Expression visit(SizeofExpressionNode node) {
		return new Int(size_t(), node.expr().allocSize());
	}

	public Expression visit(SizeofTypeNode node) {
		return new Int(size_t(), node.operand().allocSize());
	}

	public Expression visit(VariableNode node) {
		if (node.entity().isConstant()) {
			return transformExpr(node.entity().value());
		}
		Var var = ref(node.entity());
		return node.isLoadable() ? var : addressOf(var);
	}

	public Expression visit(IntegerLiteralNode node) {
		return new Int(asmType(node.type()), node.value());
	}

	public Expression visit(StringLiteralNode node) {
		return new Str(asmType(node.type()), node.entry());
	}

	//
	// Utilities -------------------------------------------------------------------------------------------
	//

	private boolean isPointerDiff(Operator operator, Type l, Type r) {
		return operator == Operator.SUB && l.isPointer() && r.isPointer();
	}

	private boolean isPointerArithmetic(Operator operator, Type operandType) {
		switch (operator) {
			case ADD:
			case SUB:
				return operandType.isPointer();
			default:
				return false;
		}
	}

	private Expression ptrBaseSize(Type t) {
		return new Int(ptrdiff_t(), t.baseType().size());
	}

	// unary ops -> binary ops
	private Operator binOp(String uniOp) {
		return uniOp.equals("++") ? Operator.ADD : Operator.SUB;
	}

	// #@@range/addressOf{
	private Expression addressOf(Expression expression) {
		return expression.addressNode(ptr_t());
	}
	// #@@}

	// #@@range/ref{
	private Var ref(Entity ent) {
		return new Var(varType(ent.type()), ent);
	}
	// #@@}

	// mem(ent) -> (Mem (Var ent))
	private Mem mem(Entity ent) {
		return new Mem(asmType(ent.type().baseType()), ref(ent));
	}

	// mem(expression) -> (Mem expression)
	// #@@range/mem{
	private Mem mem(Expression expression, Type t) {
		return new Mem(asmType(t), expression);
	}
	// #@@}

	// #@@range/ptrdiff{
	private Int ptrdiff(long n) {
		return new Int(ptrdiff_t(), n);
	}
	// #@@}

	// #@@range/size{
	private Int size(long n) {
		return new Int(size_t(), n);
	}
	// #@@}

	// #@@range/imm{
	private Int imm(Type operandType, long n) {
		if (operandType.isPointer()) {
			return new Int(ptrdiff_t(), n);
		} else {
			return new Int(int_t(), n);
		}
	}
	// #@@}

	private Type pointerTo(Type t) {
		return typeTable.pointerTo(t);
	}

	private io.vincent.compiler.c.asm.Type asmType(Type t) {
		if (t.isVoid()) return int_t();
		return io.vincent.compiler.c.asm.Type.get(t.size());
	}

	private io.vincent.compiler.c.asm.Type varType(Type t) {
		if (!t.isScalar()) {
			return null;
		}
		return io.vincent.compiler.c.asm.Type.get(t.size());
	}

	private io.vincent.compiler.c.asm.Type int_t() {
		return io.vincent.compiler.c.asm.Type.get(typeTable.intSize());
	}

	private io.vincent.compiler.c.asm.Type size_t() {
		return io.vincent.compiler.c.asm.Type.get(typeTable.longSize());
	}

	private io.vincent.compiler.c.asm.Type ptr_t() {
		return io.vincent.compiler.c.asm.Type.get(typeTable.pointerSize());
	}

	private io.vincent.compiler.c.asm.Type ptrdiff_t() {
		return io.vincent.compiler.c.asm.Type.get(typeTable.longSize());
	}

	private void error(Node n, String msg) {
		errorHandler.error(n.location(), msg);
	}
}
