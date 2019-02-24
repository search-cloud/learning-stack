package io.vincent.compiler.ir;

import io.vincent.compiler.asm.MemoryReference;
import io.vincent.compiler.asm.Operand;
import io.vincent.compiler.asm.Type;
import io.vincent.compiler.entity.Entity;

/**
 * 取地址表达式
 */
public class Addr extends Expression {
	// 表达式
	private Entity entity;

	public Addr(Type type, Entity entity) {
		super(type);
		this.entity = entity;
	}

	public boolean isAddr() {
		return true;
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
