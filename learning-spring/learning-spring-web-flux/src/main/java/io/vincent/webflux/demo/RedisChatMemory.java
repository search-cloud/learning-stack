package io.vincent.webflux.demo;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 基于 Redis 的分布式上下文存储，解决单点问题
 * 数据结构：Hash 存储（会话ID为键，消息列表为值）
 * 优化点：Pipeline 批量操作、淘汰策略（按 Token 或消息数量）
 * 参考：网页1、网页7
 */
public class RedisChatMemory implements ChatMemory {
    private final RedisTemplate<String, Message> redisTemplate;
    private static final String KEY_PREFIX = "chat:";

    public RedisChatMemory(RedisTemplate<String, Message> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        String key = KEY_PREFIX + conversationId;
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
             // Set expiration only once when the key is first created.
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        }
        // 批量写入消息
        redisTemplate.opsForList().rightPushAll(key, messages);
    }

    @Override
    public List<Message> get(String conversationId, int limit) {
        String key = KEY_PREFIX + conversationId;

        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            return Collections.emptyList();
        }

        Long sizeLong = redisTemplate.opsForList().size(key);
        if (sizeLong == null) {
            return Collections.emptyList();
        }
        
        long size = sizeLong;
        long start = Math.max(0, size - limit);
        return redisTemplate.opsForList().range(key, start, size - 1);
    }

    @Override
    public void clear(String conversationId) {
        redisTemplate.delete(KEY_PREFIX + conversationId);
    }
}