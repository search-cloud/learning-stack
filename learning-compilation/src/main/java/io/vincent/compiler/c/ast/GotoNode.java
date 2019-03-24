package io.vincent.compiler.c.ast;

import io.vincent.compiler.c.context.Dumper;

public class GotoNode extends StatementNode {
    protected String target;

    public GotoNode(Location loc, String target) {
        super(loc);
        this.target = target;
    }

    public String target() {
        return target;
    }

    public void _dump(Dumper d) {
        d.printMember("target", target);
    }

    public <S,E> S accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
