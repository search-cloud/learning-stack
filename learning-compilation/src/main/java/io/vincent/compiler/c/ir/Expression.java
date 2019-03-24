package io.vincent.compiler.c.ir;

import io.vincent.compiler.c.asm.ImmediateValue;
import io.vincent.compiler.c.asm.MemoryReference;
import io.vincent.compiler.c.asm.Operand;
import io.vincent.compiler.c.asm.Type;
import io.vincent.compiler.c.entity.Entity;

/**
 * 表达式 抽象
 */
public abstract class Expression implements Dumpable {
    final Type type;

    Expression(Type type) {
        this.type = type;
    }

    public Type type() { return type; }

    public boolean isVar() { return false; }
    public boolean isAddr() { return false; }

    public boolean isConstant() { return false; }

    public ImmediateValue asmValue() {
        throw new Error("Expr#asmValue called");
    }

    public Operand address() {
        throw new Error("Expr#address called");
    }

    public MemoryReference memref() {
        throw new Error("Expr#memref called");
    }

    // #@@range/addressNode{
    public Expression addressNode(Type type) {
        throw new Error("unexpected node for LHS: " + getClass());
    }
    // #@@}

    public Entity getEntityForce() {
        return null;
    }

    abstract public <S,E> E accept(IRVisitor<S,E> visitor);

    public void dump(Dumper d) {
        d.printClass(this);
        d.printMember("type", type);
        _dump(d);
    }

    abstract public void _dump(Dumper d);
}
