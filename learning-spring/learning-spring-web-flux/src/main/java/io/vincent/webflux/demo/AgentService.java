package io.vincent.webflux.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

/**
 * 代理服务，整合模型调用、上下文管理和工具调用
 * 核心功能：流式响应生成、异常处理、会话生命周期管理
 */
@Service
public class AgentService {
    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    public AgentService(ChatClient chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.chatMemory = chatMemory;
    }

    /**
     * 生成字符串流.
     *
     * @param prompt    参数
     * @param sessionId 上下文id
     * @return 答案的字符串流.
     */
    public Flux<String> generateStream(String prompt, String sessionId) {
    return chatClient.prompt()
            .user(prompt).advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, sessionId))
            .stream().content()
            .onErrorResume(e -> {
                // 返回错误信息而不是抛出异常，保持流的完整性
                return Flux.just(String.format("{\"error\": {\"message\": \"%s\"}}", e.getMessage()));
            });
}

}