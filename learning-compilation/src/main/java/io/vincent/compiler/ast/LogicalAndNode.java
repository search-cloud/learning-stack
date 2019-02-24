package io.vincent.compiler.ast;

public class LogicalAndNode extends BinaryOpNode {
    public LogicalAndNode(ExpressionNode left, ExpressionNode right) {
        super(left, "&&", right);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
