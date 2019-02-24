package io.vincent.compiler.sysdep;

/**
 * 代码生成器
 */
public interface CodeGenerator {
    AssemblyCode generate(io.vincent.compiler.ir.IR ir);
}
