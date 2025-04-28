package io.vincent.learning.stack.jvm.asm;

public class DefaultEchoService implements EchoService {
    @Override
    public String echo(String message) {
        return "[ECHO] " + message;
    }
}