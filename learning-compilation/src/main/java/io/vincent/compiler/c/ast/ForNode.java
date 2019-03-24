package io.vincent.compiler.c.ast;
import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.type.ref.IntegerTypeRef;

public class ForNode extends StatementNode {
    protected StatementNode init;
    protected ExpressionNode cond;
    protected StatementNode incr;
    protected StatementNode body;

    public ForNode(Location loc,
                   ExpressionNode init, ExpressionNode cond, ExpressionNode incr, StatementNode body) {
        super(loc);
        if (init != null) {
            this.init = new ExpressionStatementNode(init.location(), init);
        } else {
            this.init = null;
        }
        if (cond != null) {
            this.cond = cond;
        } else {
            this.cond = new IntegerLiteralNode(null, IntegerTypeRef.intRef(), 1);
        }
        if (incr != null) {
            this.incr = new ExpressionStatementNode(incr.location(), incr);
        } else {
            this.incr = null;
        }
        this.body = body;
    }

    public StatementNode init() {
        return init;
    }

    public ExpressionNode cond() {
        return cond;
    }

    public StatementNode incr() {
        return incr;
    }

    public StatementNode body() {
        return body;
    }

    public void _dump(Dumper d) {
        d.printMember("init", init);
        d.printMember("condition", cond);
        d.printMember("incr", incr);
        d.printMember("body", body);
    }

    public <S,E> S accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
