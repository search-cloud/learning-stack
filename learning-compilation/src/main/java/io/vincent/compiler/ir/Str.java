package io.vincent.compiler.ir;

import io.vincent.compiler.asm.*;
import io.vincent.compiler.entity.ConstantEntry;

/**
 * 字符串表达式
 */
public class Str extends Expression {
	// 常量符号
	protected ConstantEntry entry;

	public Str(Type type, ConstantEntry entry) {
		super(type);
		this.entry = entry;
	}

	public ConstantEntry entry() {
		return entry;
	}

	public Symbol symbol() {
		return entry.symbol();
	}

	public boolean isConstant() {
		return true;
	}

	public MemoryReference memref() {
		return entry.memref();
	}

	public Operand address() {
		return entry.address();
	}

	public ImmediateValue asmValue() {
		return entry.address();
	}

	public <S, E> E accept(IRVisitor<S, E> visitor) {
		return visitor.visit(this);
	}

	public void _dump(Dumper d) {
		d.printMember("entry", entry.toString());
	}
}
