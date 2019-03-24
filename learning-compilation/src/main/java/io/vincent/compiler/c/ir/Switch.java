package io.vincent.compiler.c.ir;

import io.vincent.compiler.c.asm.Label;
import io.vincent.compiler.c.ast.Location;

import java.util.List;

/**
 * Switch 语句
 */
public class Switch extends Statement {
	// 条件表达式
	protected Expression condition;
	// case 分支, 由条件和目标组成的链表
	protected List<Case> cases;
	// default 语句, 结束语句
	protected Label defaultLabel, endLabel;

	public Switch(Location loc, Expression condition,
	              List<Case> cases, Label defaultLabel, Label endLabel) {
		super(loc);
		this.condition = condition;
		this.cases = cases;
		this.defaultLabel = defaultLabel;
		this.endLabel = endLabel;
	}

	public Expression cond() {
		return condition;
	}

	public List<Case> cases() {
		return cases;
	}

	public Label defaultLabel() {
		return defaultLabel;
	}

	public Label endLabel() {
		return endLabel;
	}

	public <S, E> S accept(IRVisitor<S, E> visitor) {
		return visitor.visit(this);
	}

	public void _dump(Dumper d) {
		d.printMember("condition", condition);
		d.printMembers("cases", cases);
		d.printMember("defaultLabel", defaultLabel);
		d.printMember("endLabel", endLabel);
	}
}
