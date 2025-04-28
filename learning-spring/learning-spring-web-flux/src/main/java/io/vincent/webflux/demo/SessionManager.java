package io.vincent.webflux.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 跟踪会话状态，自动清理过期会话
 * 关键逻辑：sessionTimeout 控制自动清理，默认30分钟
 */
@Component
public class SessionManager {
    private final Map<String, Long> sessionLastAccess = new ConcurrentHashMap<>();
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000; // 30分钟

    // 更新会话活跃时间
    public void updateAccessTime(String sessionId) {
        sessionLastAccess.put(sessionId, System.currentTimeMillis());
    }

    // 清理过期会话
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void cleanupExpiredSessions() {
        long currentTime = System.currentTimeMillis();
        sessionLastAccess.entrySet().removeIf(entry -> 
            currentTime - entry.getValue() > SESSION_TIMEOUT
        );
    }
}