package io.vincent.compiler.c.sysdep;
import io.vincent.compiler.c.exception.IPCException;

public interface Assembler {
    void assemble(String srcPath, String destPath, AssemblerOptions opts) throws IPCException;
}
