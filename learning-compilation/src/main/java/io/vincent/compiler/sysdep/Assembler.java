package io.vincent.compiler.sysdep;
import io.vincent.compiler.exception.IPCException;

public interface Assembler {
    void assemble(String srcPath, String destPath, AssemblerOptions opts) throws IPCException;
}
