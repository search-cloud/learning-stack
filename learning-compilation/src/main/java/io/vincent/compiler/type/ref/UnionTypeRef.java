package io.vincent.compiler.type.ref;
import io.vincent.compiler.ast.Location;
import io.vincent.compiler.type.TypeRef;

public class UnionTypeRef extends TypeRef {
    protected String name;

    public UnionTypeRef(String name) {
        this(null, name);
    }

    public UnionTypeRef(Location loc, String name) {
        super(loc);
        this.name = name;
    }

    public boolean isUnion() {
        return true;
    }

    public boolean equals(Object other) {
        if (!(other instanceof UnionTypeRef)) return false;
        return name.equals(((UnionTypeRef)other).name);
    }

    public String name() {
        return name;
    }

    public String toString() {
        return "union " + name;
    }
}
