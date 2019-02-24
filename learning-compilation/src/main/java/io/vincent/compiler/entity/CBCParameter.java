package io.vincent.compiler.entity;
import io.vincent.compiler.ast.TypeNode;
import io.vincent.compiler.context.Dumper;

public class CBCParameter extends DefinedVariable {
    public CBCParameter(TypeNode type, String name) {
        super(false, type, name, null);
    }

    public boolean isParameter() {
        return true;
    }

    public void _dump(Dumper d) {
        d.printMember("name", name);
        d.printMember("typeNode", typeNode);
    }
}
