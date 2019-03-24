package io.vincent.compiler.c.entity;
import io.vincent.compiler.c.ast.TypeNode;
import io.vincent.compiler.c.context.Dumper;

public class UndefinedVariable extends Variable {
    public UndefinedVariable(TypeNode t, String name) {
        super(false, t, name);
    }

    public boolean isDefined() { return false; }
    public boolean isPrivate() { return false; }
    public boolean isInitialized() { return false; }

    public void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("isPrivate", isPrivate());
        d.printMember("typeNode", typeNode);
    }

    public <T> T accept(EntityVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
