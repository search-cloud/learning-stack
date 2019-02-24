package io.vincent.compiler.ast;

import io.vincent.compiler.context.Dumper;
import io.vincent.compiler.type.*;

import java.util.List;

abstract public class CompositeTypeDefinition extends TypeDefinition {
    protected List<Slot> members;

    public CompositeTypeDefinition(Location loc, TypeRef ref,
                                   String name, List<Slot> membs) {
        super(loc, ref, name);
        members = membs;
    }

    public boolean isCompositeType() {
        return true;
    }

    abstract public String kind();

    public List<Slot> members() {
        return members;
    }

    public void _dump(Dumper d) {
        d.printMember("name", name);
        d.printNodeList("members", members);
    }
}
