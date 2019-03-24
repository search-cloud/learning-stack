package io.vincent.compiler.c.entity;

import io.vincent.compiler.c.ast.Location;
import io.vincent.compiler.c.context.Dumpable;
import io.vincent.compiler.c.context.Dumper;
import io.vincent.compiler.c.type.TypeRef;
import io.vincent.compiler.c.type.ref.ParamTypeRefs;

import java.util.ArrayList;
import java.util.List;

public class Params extends ParamSlots<CBCParameter>
        implements Dumpable {
    public Params(Location loc, List<CBCParameter> paramDescs) {
        super(loc, paramDescs, false);
    }

    public List<CBCParameter> parameters() {
        return paramDescriptors;
    }

    public ParamTypeRefs parametersTypeRef() {
        List<TypeRef> typerefs = new ArrayList<TypeRef>();
        for (CBCParameter param : paramDescriptors) {
            typerefs.add(param.typeNode().typeRef());
        }
        return new ParamTypeRefs(location, typerefs, vararg);
    }

    public boolean equals(Object other) {
        return (other instanceof Params) && equals((Params)other);
    }

    public boolean equals(Params other) {
        return other.vararg == vararg
                && other.paramDescriptors.equals(paramDescriptors);
    }

    public void dump(Dumper d) {
        d.printNodeList("parameters", parameters());
    }
}
