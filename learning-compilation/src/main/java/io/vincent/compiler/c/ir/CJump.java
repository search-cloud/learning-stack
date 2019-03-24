package io.vincent.compiler.c.ir;

import io.vincent.compiler.c.asm.Label;
import io.vincent.compiler.c.ast.Location;

/**
 * 有条件跳转语句
 */
public class CJump extends Statement {

    // 条件表达式
    protected Expression condition;
    // 条件真跳转的目标
    private Label thenLabel;
    // 条件假跳转的目标
    private Label elseLabel;

    public CJump(Location location, Expression condition, Label thenLabel, Label elseLabel) {
        super(location);
        this.condition = condition;
        this.thenLabel = thenLabel;
        this.elseLabel = elseLabel;
    }

    public Expression condition() {
        return condition;
    }

    public Label thenLabel() {
        return thenLabel;
    }

    public Label elseLabel() {
        return elseLabel;
    }

    public <S,E> S accept(IRVisitor<S,E> visitor) {
        return visitor.visit(this);
    }

    public void _dump(Dumper d) {
        d.printMember("condition", condition);
        d.printMember("thenLabel", thenLabel);
        d.printMember("elseLabel", elseLabel);
    }
}
