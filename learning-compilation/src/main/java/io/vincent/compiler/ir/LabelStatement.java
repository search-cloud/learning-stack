package io.vincent.compiler.ir;

import io.vincent.compiler.asm.Label;
import io.vincent.compiler.ast.Location;

/**
 * 标签--跳转目标
 */
public class LabelStatement extends Statement {
    protected Label label;

    public LabelStatement(Location loc, Label label) {
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
