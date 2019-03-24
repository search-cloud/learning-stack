package io.vincent.compiler.c.entity;

import io.vincent.compiler.c.ast.BlockNode;
import io.vincent.compiler.c.ast.TypeNode;
import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.ir.Statement;

import java.util.List;

public class DefinedFunction extends Function {
    protected Params params;
    protected BlockNode body;
    protected LocalScope scope;
    protected List<Statement> ir;

    public DefinedFunction(boolean priv, TypeNode type,
            String name, Params params, BlockNode body) {
        super(priv, type, name);
        this.params = params;
        this.body = body;
    }

    public boolean isDefined() {
        return true;
    }

    public List<CBCParameter> parameters() {
        return params.parameters();
    }

    public BlockNode body() {
        return body;
    }

    public List<Statement> ir() {
        return ir;
    }

    public void setIR(List<Statement> ir) {
        this.ir = ir;
    }

    public void setScope(LocalScope scope) {
        this.scope = scope;
    }

    public LocalScope lvarScope() {
        return body().scope();
    }

    /**
     * Returns function local variables.
     * Does NOT include paramters.
     * Does NOT include static local variables.
     */
    public List<DefinedVariable> localVariables() {
        return scope.allLocalVariables();
    }

    public void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("isPrivate", isPrivate);
        d.printMember("params", params);
        d.printMember("body", body);
    }

    public <T> T accept(EntityVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
