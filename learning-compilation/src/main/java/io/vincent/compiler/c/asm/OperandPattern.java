package io.vincent.compiler.c.asm;

public interface OperandPattern {
    public boolean match(Operand operand);
}
