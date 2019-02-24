package io.vincent.compiler.sysdep;

import io.vincent.compiler.exception.IPCException;
import io.vincent.compiler.utils.CommandUtils;
import io.vincent.compiler.utils.ErrorHandler;

import java.util.ArrayList;
import java.util.List;

class GNUAssembler implements Assembler {
    ErrorHandler errorHandler;

    GNUAssembler(ErrorHandler h) {
        this.errorHandler = h;
    }

    // #@@range/assemble{
    public void assemble(String srcPath, String destPath,
                            AssemblerOptions opts) throws IPCException {
        List<String> cmd = new ArrayList<String>();
        cmd.add("as");
        cmd.addAll(opts.args);
        cmd.add("-o");
        cmd.add(destPath);
        cmd.add(srcPath);
        CommandUtils.invoke(cmd, errorHandler, opts.verbose);
    }
    // #@@}
}
