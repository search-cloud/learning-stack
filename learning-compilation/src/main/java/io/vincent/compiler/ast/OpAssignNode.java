package io.vincent.compiler.ast;

public class OpAssignNode extends AbstractAssignNode {
    protected String operator;

    public OpAssignNode(ExpressionNode lhs, String op, ExpressionNode rhs) {
        super(lhs, rhs);
        this.operator = op;
    }

    public String operator() {
        return operator;
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
