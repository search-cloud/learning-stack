package io.vincent.compiler.asm;

public interface OperandPattern {
    public boolean match(Operand operand);
}
