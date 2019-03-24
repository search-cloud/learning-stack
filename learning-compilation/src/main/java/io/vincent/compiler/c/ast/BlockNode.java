package io.vincent.compiler.c.ast;

import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.entity.DefinedVariable;
import io.vincent.compiler.c.entity.LocalScope;

import java.util.List;

public class BlockNode extends StatementNode {
    protected List<DefinedVariable> variables;
    protected List<StatementNode> stmts;
    protected LocalScope scope;

    public BlockNode(Location loc, List<DefinedVariable> vars, List<StatementNode> stmts) {
        super(loc);
        this.variables = vars;
        this.stmts = stmts;
    }

    public List<DefinedVariable> variables() {
        return variables;
    }

    public List<StatementNode> stmts() {
        return stmts;
    }

    public StatementNode tailStmt() {
        if (stmts.isEmpty()) return null;
        return stmts.get(stmts.size() - 1);
    }

    public LocalScope scope() {
        return scope;
    }

    public void setScope(LocalScope scope) {
        this.scope = scope;
    }

    public void _dump(Dumper d) {
        d.printNodeList("variables", variables);
        d.printNodeList("stmts", stmts);
    }

    public <S,E> S accept(ASTVisitor<S,E> visitor) {
        return visitor.visit(this);
    }
}
