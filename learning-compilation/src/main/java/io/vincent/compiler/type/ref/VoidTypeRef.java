package io.vincent.compiler.type.ref;
import io.vincent.compiler.ast.Location;
import io.vincent.compiler.type.TypeRef;

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
