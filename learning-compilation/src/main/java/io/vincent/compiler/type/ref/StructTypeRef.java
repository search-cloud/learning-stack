package io.vincent.compiler.type.ref;
import io.vincent.compiler.ast.Location;
import io.vincent.compiler.type.TypeRef;

public class StructTypeRef extends TypeRef {
    protected String name;

    public StructTypeRef(String name) {
        this(null, name);
    }

    public StructTypeRef(Location loc, String name) {
        super(loc);
        this.name = name;
    }

    public boolean isStruct() {
        return true;
    }

    public boolean equals(Object other) {
        if (!(other instanceof StructTypeRef)) return false;
        return name.equals(((StructTypeRef)other).name);
    }

    public String name() {
        return name;
    }

    public String toString() {
        return "struct " + name;
    }
}
