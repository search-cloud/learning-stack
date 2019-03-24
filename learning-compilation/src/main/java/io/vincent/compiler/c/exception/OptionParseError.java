package io.vincent.compiler.c.exception;

public class OptionParseError extends Error {
    public OptionParseError(String msg) {
        super(msg);
    }
}
