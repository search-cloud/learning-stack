package io.vincent.compiler.c.ast;
import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.type.Type;

public class DereferenceNode extends LHSNode {
    private ExpressionNode expr;

    public DereferenceNode(ExpressionNode expr) {
        this.expr = expr;
    }

    protected Type origType() {
        return expr.type().baseType();
    }

    public ExpressionNode expr() {
        return expr;
    }

    public void setExpr(ExpressionNode expr) {
        this.expr = expr;
    }

    public Location location() {
        return expr.location();
    }

    public void _dump(Dumper d) {
        if (type != null) {
            d.printMember("type", type);
        }
        d.printMember("expression", expr);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
