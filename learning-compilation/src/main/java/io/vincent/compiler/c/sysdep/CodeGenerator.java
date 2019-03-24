package io.vincent.compiler.c.sysdep;

import io.vincent.compiler.c.ir.IR;

/**
 * 代码生成器
 */
public interface CodeGenerator {
    AssemblyCode generate(IR ir);
}
