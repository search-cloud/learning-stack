package io.vincent.compiler.ir;
import io.vincent.compiler.asm.Label;

/**
 * case 语句
 */
public class Case implements Dumpable {
    // 条件
    public long value;
    // 跳转的目标
    public Label label;

    public Case(long value, Label label) {
        this.value = value;
        this.label = label;
    }

    public void dump(Dumper d) {
        d.printClass(this);
        d.printMember("value", value);
        d.printMember("label", label);
    }
}
