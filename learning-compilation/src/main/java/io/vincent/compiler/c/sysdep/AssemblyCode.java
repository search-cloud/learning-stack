package io.vincent.compiler.c.sysdep;
import java.io.PrintStream;

/**
 * 组装代码
 */
public interface AssemblyCode {
    String toSource();
    void dump();
    void dump(PrintStream s);
}
