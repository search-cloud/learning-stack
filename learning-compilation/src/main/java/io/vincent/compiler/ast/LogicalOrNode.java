package io.vincent.compiler.ast;

public class LogicalOrNode extends BinaryOpNode {
    public LogicalOrNode(ExpressionNode left, ExpressionNode right) {
        super(left, "||", right);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
