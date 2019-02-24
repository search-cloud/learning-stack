package io.vincent.compiler.ast;
import io.vincent.compiler.context.Dumper;
import io.vincent.compiler.type.*;

public class CondExpressionNode extends ExpressionNode {
    protected ExpressionNode cond, thenExpr, elseExpr;

    public CondExpressionNode(ExpressionNode cond, ExpressionNode t, ExpressionNode e) {
        super();
        this.cond = cond;
        this.thenExpr = t;
        this.elseExpr = e;
    }

    public Type type() {
        return thenExpr.type();
    }

    public ExpressionNode cond() {
        return cond;
    }

    public ExpressionNode thenExpr() {
        return thenExpr;
    }

    public void setThenExpr(ExpressionNode expr) {
        this.thenExpr = expr;
    }

    public ExpressionNode elseExpr() {
        return elseExpr;
    }

    public void setElseExpr(ExpressionNode expr) {
        this.elseExpr = expr;
    }

    public Location location() {
        return cond.location();
    }

    public void _dump(Dumper d) {
        d.printMember("condition", cond);
        d.printMember("thenExpr", thenExpr);
        d.printMember("elseExpr", elseExpr);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
