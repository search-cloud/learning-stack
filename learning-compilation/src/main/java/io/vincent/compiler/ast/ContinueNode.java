package io.vincent.compiler.ast;

import io.vincent.compiler.context.Dumper;

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
