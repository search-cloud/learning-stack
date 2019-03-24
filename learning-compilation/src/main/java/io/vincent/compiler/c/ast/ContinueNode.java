package io.vincent.compiler.c.ast;

import io.vincent.compiler.c.context.Dumper;

public class ContinueNode extends StatementNode {
    public ContinueNode(Location loc) {
        super(loc);
    }

    public void _dump(Dumper d) {
    }

    public <S,E> S accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
