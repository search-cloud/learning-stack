package io.vincent.compiler.c.ast;

public class UnaryArithmeticOpNode extends UnaryOpNode {
    protected long amount;

    public UnaryArithmeticOpNode(String op, ExpressionNode expr) {
        super(op, expr);
        amount = 1;
    }

    public void setExpr(ExpressionNode expr) {
        this.expr = expr;
    }

    public long amount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
