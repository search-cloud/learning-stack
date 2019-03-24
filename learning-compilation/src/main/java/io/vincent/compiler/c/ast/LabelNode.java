package io.vincent.compiler.c.ast;

import io.vincent.compiler.c.context.Dumper;

public class LabelNode extends StatementNode {
    protected String name;
    protected StatementNode stmt;

    public LabelNode(Location loc, String name, StatementNode stmt) {
        super(loc);
        this.name = name;
        this.stmt = stmt;
    }

    public String name() {
        return name;
    }

    public StatementNode stmt() {
        return stmt;
    }

    public void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("stmt", stmt);
    }

    public <S,E> S accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
