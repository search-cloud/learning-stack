package io.vincent.compiler.c.type.ref;
import io.vincent.compiler.c.ast.Location;
import io.vincent.compiler.c.type.TypeRef;

public class VoidTypeRef extends TypeRef {
    public VoidTypeRef() {
        super(null);
    }

    public VoidTypeRef(Location loc) {
        super(loc);
    }

    public boolean isVoid() {
        return true;
    }

    public boolean equals(Object other) {
        return (other instanceof VoidTypeRef);
    }

    public String toString() {
        return "void";
    }
}
