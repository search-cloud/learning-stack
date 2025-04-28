package io.vincent.webflux.demo.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * 配置 RedisTemplate 序列化规则，优化存储性能
 * 关键配置：StringRedisSerializer 避免二进制乱码
 */
@Configuration
public class OpenAIConfiguration {

    @Bean
    public RedisTemplate<String, Message> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Message> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));
        return template;
    }
    
    @Bean
    public ChatMemory redisChatMemory() {
        return new InMemoryChatMemory();
    }

//    @Bean
//    public ChatMemory redisChatMemory(RedisTemplate<String, Message> redisTemplate) {
//        return new RedisChatMemory(redisTemplate);
//    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
    }


    /**
     * 流式 API 客户端，集成模型调用与上下文管理
     * 功能：同步/异步调用、Prompt 动态构建、响应格式化
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder charClientBuilder, ChatMemory chatMemory) {

        return charClientBuilder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(), // 上下文管理
//                        VectorStoreChatMemoryAdvisor.builder(chatMemory).build(),
                        SafeGuardAdvisor.builder().sensitiveWords(List.of("敏感词")).build(),                   // 敏感词过滤
                        new SimpleLoggerAdvisor()                 // 日志记录
                )
                .defaultSystem("你是一个专业的客服助手")        // 系统提示词
                .build();
    }
}
