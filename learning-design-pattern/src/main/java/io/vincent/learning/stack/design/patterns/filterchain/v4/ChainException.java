package io.vincent.learning.stack.design.patterns.filterchain.v4;

import java.io.PrintWriter;
import java.time.Instant;
import java.util.UUID;

public class ChainException extends RuntimeException {
    private final Instant timestamp = Instant.now();
    private final String errorCode;
    
    public ChainException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "CHAIN_ERR_" + UUID.randomUUID().toString().substring(0,8);
    }
    
    // 异常序列化支持
    @Override
    public void printStackTrace(PrintWriter s) {
        s.printf("[%s][%s] ", timestamp, errorCode);
        super.printStackTrace(s);
    }
}