package io.vincent.compiler.c.ast;

import io.vincent.compiler.c.context.Dumper;

public class ExpressionStatementNode extends StatementNode {
    protected ExpressionNode expr;

    public ExpressionStatementNode(Location loc, ExpressionNode expr) {
        super(loc);
        this.expr = expr;
    }

    public ExpressionNode expr() {
        return expr;
    }

    public void setExpr(ExpressionNode expr) {
        this.expr = expr;
    }

    public void _dump(Dumper d) {
        d.printMember("expression", expr);
    }

    public <S,E> S accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
