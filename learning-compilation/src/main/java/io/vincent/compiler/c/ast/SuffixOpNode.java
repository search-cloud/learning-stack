package io.vincent.compiler.c.ast;

public class SuffixOpNode extends UnaryArithmeticOpNode {
    public SuffixOpNode(String op, ExpressionNode expr) {
        super(op, expr);
    }

    public <S,E> E accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
