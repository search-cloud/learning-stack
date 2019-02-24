package io.vincent.compiler.sysdep;

import io.vincent.compiler.asm.Type;
import io.vincent.compiler.type.TypeTable;
import io.vincent.compiler.utils.ErrorHandler;

public class X86Linux implements Platform {
    public TypeTable typeTable() {
        return TypeTable.ilp32();
    }

    public CodeGenerator codeGenerator(CodeGeneratorOptions opts, ErrorHandler h) {
        return new io.vincent.compiler.sysdep.x86.CodeGenerator(opts, naturalType(), h);
    }

    private Type naturalType() {
        return Type.INT32;
    }

    public Assembler assembler(ErrorHandler h) {
        return new GNUAssembler(h);
    }

    public Linker linker(ErrorHandler h) {
        return new GNULinker(h);
    }
}
