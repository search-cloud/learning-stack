package io.vincent.compiler.ir;

import io.vincent.compiler.asm.*;

/**
 * Int 表达式
 */
public class Int extends Expression {
	// 值
	protected long value;

	public Int(Type type, long value) {
		super(type);
		this.value = value;
	}

	public long value() {
		return value;
	}

	public boolean isConstant() {
		return true;
	}

	public ImmediateValue asmValue() {
		return new ImmediateValue(new IntegerLiteral(value));
	}

	public MemoryReference memref() {
		throw new Error("must not happen: IntValue#memref");
	}

	public <S, E> E accept(IRVisitor<S, E> visitor) {
		return visitor.visit(this);
	}

	public void _dump(Dumper d) {
		d.printMember("value", value);
	}
}
