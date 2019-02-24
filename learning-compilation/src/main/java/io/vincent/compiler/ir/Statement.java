package io.vincent.compiler.ir;
import io.vincent.compiler.ast.Location;

/**
 * 语言语句抽象
 */
public abstract class Statement implements Dumpable {
    // 位置
    protected Location location;

    public Statement(Location location) {
        this.location = location;
    }

    abstract public <S,E> S accept(IRVisitor<S,E> visitor);

    public Location location() {
        return location;
    }

    public void dump(Dumper d) {
        d.printClass(this, location);
        _dump(d);
    }

    abstract public void _dump(Dumper d);
}
