package io.vincent.compiler.c.type;

import io.vincent.compiler.c.ast.Location;

/**
 * 类型引用
 */
public abstract class TypeRef {
    /**
     * 引用的位置
     */
    protected Location location;

    public TypeRef(Location loc) {
        this.location = loc;
    }

    public Location location() {
        return location;
    }

    public int hashCode() {
        return toString().hashCode();
    }
}
