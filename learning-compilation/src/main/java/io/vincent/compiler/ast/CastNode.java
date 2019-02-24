package io.vincent.compiler.ast;
import io.vincent.compiler.context.Dumper;
import io.vincent.compiler.type.*;

public class CastNode extends ExpressionNode {
    protected TypeNode typeNode;
    protected ExpressionNode expr;

    public CastNode(Type t, ExpressionNode expr) {
        this(new TypeNode(t), expr);
    }

    public CastNode(TypeNode t, ExpressionNode expr) {
        this.typeNode = t;
        this.expr = expr;
    }

    public Type type() {
        return typeNode.type();
    }

    public TypeNode typeNode() {
        return typeNode;
    }

    public ExpressionNode expr() {
        return expr;
    }

    public boolean isLvalue() { return expr.isLvalue(); }
    public boolean isAssignable() { return expr.isAssignable(); }

    public boolean isEffectiveCast() {
        return type().size() > expr.type().size();
    }

    public Location location() {
        return typeNode.location();
    }

    public void _dump(Dumper d) {
        d.printMember("typeNode", typeNode);
        d.printMember("expression", expr);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
