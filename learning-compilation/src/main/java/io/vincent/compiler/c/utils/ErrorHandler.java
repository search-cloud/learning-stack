package io.vincent.compiler.c.utils;

import io.vincent.compiler.c.ast.Location;

import java.io.OutputStream;
import java.io.PrintStream;

public class ErrorHandler {
    protected String programId;
    protected PrintStream stream;
    protected long nError;
    protected long nWarning;

    public ErrorHandler(String progid) {
        programId = progid;
        stream = System.err;
    }

    public ErrorHandler(String programId, OutputStream stream) {
        this.programId = programId;
        this.stream = new PrintStream(stream);
    }

    public void error(Location loc, String msg) {
        error(loc.toString() + ": " + msg);
    }

    public void error(String msg) {
        stream.println(programId + ": error: " + msg);
        nError++;
    }

    public void warn(Location loc, String msg) {
        warn(loc.toString() + ": " + msg);
    }

    public void warn(String msg) {
        stream.println(programId + ": warning: " + msg);
        nWarning++;
    }

    public boolean errorOccurred() {
        return (nError > 0);
    }
}
