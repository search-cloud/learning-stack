package io.vincent.compiler.type;
import io.vincent.compiler.ast.Location;

abstract public class NamedType implements Type {
    protected String name;
    protected Location location;

    public NamedType(String name, Location loc) {
        this.name = name;
        this.location = loc;
    }

    public String name() {
        return name;
    }

    public Location location() {
        return location;
    }
}
