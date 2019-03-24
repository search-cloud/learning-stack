package io.vincent.compiler.c.ast;
import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.type.Type;

public class UnaryOpNode extends ExpressionNode {
    protected String operator;
    protected ExpressionNode expr;
    protected Type opType;

    public UnaryOpNode(String op, ExpressionNode expr) {
        this.operator = op;
        this.expr = expr;
    }

    public String operator() {
        return operator;
    }

    public Type type() {
        return expr.type();
    }

    public void setOpType(Type t) {
        this.opType = t;
    }

    public Type opType() {
        return opType;
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
        d.printMember("operator", operator);
        d.printMember("expression", expr);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
