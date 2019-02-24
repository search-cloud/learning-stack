package io.vincent.compiler.entity;

import io.vincent.compiler.asm.Symbol;
import io.vincent.compiler.ast.ExpressionNode;
import io.vincent.compiler.ast.TypeNode;
import io.vincent.compiler.context.Dumper;
import io.vincent.compiler.ir.Expression;
import io.vincent.compiler.type.Type;

public class DefinedVariable extends Variable {
    protected ExpressionNode initializer;
    protected Expression ir;
    protected long sequence;
    protected Symbol symbol;

    public DefinedVariable(boolean priv, TypeNode type,
                           String name, ExpressionNode init) {
        super(priv, type, name);
        initializer = init;
        sequence = -1;
    }

    static private long tmpSeq = 0;

    static public DefinedVariable tmp(Type t) {
        return new DefinedVariable(false,
                new TypeNode(t), "@tmp" + tmpSeq++, null);
    }

    public boolean isDefined() {
        return true;
    }

    public void setSequence(long seq) {
        this.sequence = seq;
    }

    public String symbolString() {
        return (sequence < 0) ? name : (name + "." + sequence);
    }

    public boolean hasInitializer() {
        return (initializer != null);
    }

    public boolean isInitialized() {
        return hasInitializer();
    }

    public ExpressionNode initializer() {
        return initializer;
    }

    public void setInitializer(ExpressionNode expr) {
        this.initializer = expr;
    }

    public void setIR(Expression expression) {
        this.ir = expression;
    }

    public Expression ir() { return ir; }

    public void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("isPrivate", isPrivate);
        d.printMember("typeNode", typeNode);
        d.printMember("initializer", initializer);
    }

    public <T> T accept(EntityVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
