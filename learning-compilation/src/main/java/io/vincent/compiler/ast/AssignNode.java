package io.vincent.compiler.ast;

public class AssignNode extends AbstractAssignNode {
    public AssignNode(ExpressionNode lhs, ExpressionNode rhs) {
        super(lhs, rhs);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
