package io.vincent.compiler.ir;

import io.vincent.compiler.asm.Type;

/**
 * 二元表达式
 */
public class Binary extends Expression {
	// 运算符
	private Operator operator;
	// 左右表达式
	private Expression left, right;

	public Binary(Type type, Operator operator, Expression left, Expression right) {
		super(type);
		this.operator = operator;
		this.left = left;
		this.right = right;
	}

	public Expression left() {
		return left;
	}

	public Expression right() {
		return right;
	}

	public Operator op() {
		return operator;
	}

	public <S, E> E accept(IRVisitor<S, E> visitor) {
		return visitor.visit(this);
	}

	public void _dump(Dumper d) {
		d.printMember("op", operator.toString());
		d.printMember("left", left);
		d.printMember("right", right);
	}
}
