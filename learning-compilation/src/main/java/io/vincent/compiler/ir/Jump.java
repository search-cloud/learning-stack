package io.vincent.compiler.ir;

import io.vincent.compiler.asm.Label;
import io.vincent.compiler.ast.Location;

/**
 * 无条件跳转语句
 */
public class Jump extends Statement {
    // 跳转目标
    protected Label label;

    public Jump(Location loc, Label label) {
        super(loc);
        this.label = label;
    }

    public Label label() {
        return label;
    }

    public <S,E> S accept(IRVisitor<S,E> visitor) {
        return visitor.visit(this);
    }

    public void _dump(Dumper d) {
        d.printMember("label", label);
    }
}
