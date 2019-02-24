package io.vincent.compiler.ast;

import io.vincent.compiler.context.Dumper;

public class WhileNode extends StatementNode {
    protected StatementNode body;
    protected ExpressionNode cond;

    public WhileNode(Location loc, ExpressionNode cond, StatementNode body) {
        super(loc);
        this.cond = cond;
        this.body = body;
    }

    public ExpressionNode cond() {
        return cond;
    }

    public StatementNode body() {
        return body;
    }

    public void _dump(Dumper d) {
        d.printMember("condition", cond);
        d.printMember("body", body);
    }

    public <S,E> S accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
