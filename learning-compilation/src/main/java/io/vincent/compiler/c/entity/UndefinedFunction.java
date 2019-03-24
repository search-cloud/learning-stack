package io.vincent.compiler.c.entity;

import io.vincent.compiler.c.ast.TypeNode;
import io.vincent.compiler.c.context.Dumper;

import java.util.List;

public class UndefinedFunction extends Function {
    protected Params params;

    public UndefinedFunction(TypeNode t, String name, Params params) {
        super(false, t, name);
        this.params = params;
    }

    public List<CBCParameter> parameters() {
        return params.parameters();
    }

    public boolean isDefined() {
        return false;
    }

    public void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("isPrivate", isPrivate());
        d.printMember("typeNode", typeNode);
        d.printMember("params", params);
    }

    public <T> T accept(EntityVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
