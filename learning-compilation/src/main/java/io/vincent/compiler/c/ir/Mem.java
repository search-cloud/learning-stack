package io.vincent.compiler.c.ir;

import io.vincent.compiler.c.asm.Type;

/**
 * 指针取值表达式
 */
public class Mem extends Expression {
	// 取值表达式
	protected Expression expression;

	public Mem(Type type, Expression expression) {
		super(type);
		this.expression = expression;
	}

	public Expression expr() {
		return expression;
	}

	public Expression addressNode(Type type) {
		return expression;
	}

	public <S, E> E accept(IRVisitor<S, E> visitor) {
		return visitor.visit(this);
	}

	public void _dump(Dumper d) {
		d.printMember("expression", expression);
	}
}
