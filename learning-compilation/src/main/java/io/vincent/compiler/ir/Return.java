package io.vincent.compiler.ir;

import io.vincent.compiler.ast.Location;

/**
 * return 语句
 */
public class Return extends Statement {
	// 返回值表达式
	protected Expression expression;

	public Return(Location loc, Expression expression) {
		super(loc);
		this.expression = expression;
	}

	public Expression expression() {
		return expression;
	}

	public <S, E> S accept(IRVisitor<S, E> visitor) {
		return visitor.visit(this);
	}

	public void _dump(Dumper d) {
		d.printMember("expression", expression);
	}
}
