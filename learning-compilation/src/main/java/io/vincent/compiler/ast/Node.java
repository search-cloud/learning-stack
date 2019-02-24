package io.vincent.compiler.ast;
import io.vincent.compiler.context.Dumpable;
import io.vincent.compiler.context.Dumper;

import java.io.PrintStream;

/**
 * <p>
 * 节点
 * </p>
 * 主要用在节点有关的数据结构上。
 */
public interface Node extends Dumpable {

    /**
     * @return location.
     */
    Location location();

    default void dump() {
        dump(System.out);
    }

    default void dump(PrintStream s) {
        dump(new Dumper(s));
    }

    default void dump(Dumper d) {
        d.printClass(this, location());
        _dump(d);
    }

    void _dump(Dumper d);
}
