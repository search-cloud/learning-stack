package io.vincent.webflux.demo;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一拦截异常，释放会话资源并返回错误信息
 * 关键注解：@ExceptionHandler 捕获特定异常类型
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ChatMemory chatMemory;

    public GlobalExceptionHandler(ChatMemory chatMemory) {
        this.chatMemory = chatMemory;
    }

    @ExceptionHandler(ChatServiceException.class)
    public ResponseEntity<String> handleTimeout(ChatServiceException ex) {
        String sessionId = ex.getSessionId();
        chatMemory.clear(sessionId); // 清理会话数据
        return ResponseEntity.status(504)
            .body("请求超时，会话 " + sessionId + " 已重置");
    }
}