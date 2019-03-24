package io.vincent.compiler.c.ir;

import io.vincent.compiler.c.asm.ImmediateValue;
import io.vincent.compiler.c.asm.IntegerLiteral;
import io.vincent.compiler.c.asm.MemoryReference;
import io.vincent.compiler.c.asm.Type;

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
