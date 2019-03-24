package io.vincent.compiler.c.ast;
import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.type.Type;
import io.vincent.compiler.c.type.TypeRef;

public class SizeofExpressionNode extends ExpressionNode {
    protected ExpressionNode expr;
    protected TypeNode type;

    public SizeofExpressionNode(ExpressionNode expr, TypeRef type) {
        this.expr = expr;
        this.type = new TypeNode(type);
    }

    public ExpressionNode expr() {
        return this.expr;
    }

    public void setExpr(ExpressionNode expr) {
        this.expr = expr;
    }

    public Type type() {
        return this.type.type();
    }

    public TypeNode typeNode() {
        return this.type;
    }

    public Location location() {
        return expr.location();
    }

    public void _dump(Dumper d) {
        d.printMember("expression", expr);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
