package io.vincent.compiler.c.ast;

import io.vincent.compiler.c.context.Dumper;

public class DoWhileNode extends StatementNode {
    protected StatementNode body;
    protected ExpressionNode cond;

    public DoWhileNode(Location loc, StatementNode body, ExpressionNode cond) {
        super(loc);
        this.body = body;
        this.cond = cond;
    }

    public StatementNode body() {
        return body;
    }

    public ExpressionNode cond() {
        return cond;
    }

    public void _dump(Dumper d) {
        d.printMember("body", body);
        d.printMember("condition", cond);
    }

    public <S,E> S accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
