package io.vincent.compiler.ast;

import io.vincent.compiler.context.Dumper;

public class IfNode extends StatementNode {
    protected ExpressionNode cond;
    protected StatementNode thenBody;
    protected StatementNode elseBody;

    public IfNode(Location loc, ExpressionNode c, StatementNode t, StatementNode e) {
        super(loc);
        this.cond = c;
        this.thenBody = t;
        this.elseBody = e;
    }

    public ExpressionNode cond() {
        return cond;
    }

    public StatementNode thenBody() {
        return thenBody;
    }

    public StatementNode elseBody() {
        return elseBody;
    }

    public void _dump(Dumper d) {
        d.printMember("condition", cond);
        d.printMember("thenBody", thenBody);
        d.printMember("elseBody", elseBody);
    }

    public <S,E> S accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
