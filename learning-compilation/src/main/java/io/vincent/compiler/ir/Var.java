package io.vincent.compiler.ir;

import io.vincent.compiler.asm.MemoryReference;
import io.vincent.compiler.asm.Operand;
import io.vincent.compiler.asm.Type;
import io.vincent.compiler.entity.Entity;

/**
 * 变量表达式
 */
public class Var extends Expression {
	// 变量对象
	protected Entity entity;

	public Var(Type type, Entity entity) {
		super(type);
		this.entity = entity;
	}

	public boolean isVar() {
		return true;
	}

	public Type type() {
		if (super.type() == null) {
			throw new Error("Var is too big to load by 1 insn");
		}
		return super.type();
	}

	public String name() {
		return entity.name();
	}

	public Entity entity() {
		return entity;
	}

	public Operand address() {
		return entity.address();
	}

	public MemoryReference memref() {
		return entity.memref();
	}

	// #@@range/addressNode{
	public Addr addressNode(Type type) {
		return new Addr(type, entity);
	}
	// #@@}

	public Entity getEntityForce() {
		return entity;
	}

	public <S, E> E accept(IRVisitor<S, E> visitor) {
		return visitor.visit(this);
	}

	public void _dump(Dumper d) {
		d.printMember("entity", entity.name());
	}
}
