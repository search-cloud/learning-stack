package io.vincent.webflux.demo;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态构造符合模型要求的 Prompt，支持上下文合并与参数替换
 * 关键方法：mergeSystemPrompt() 合并系统级规则与动态参数
 */
@Component
public class PromptBuilder {
    private final ChatMemory chatMemory;

    public PromptBuilder(ChatMemory chatMemory) {
        this.chatMemory = chatMemory;
    }

    /**
     * 构建包含上下文和参数的 Prompt
     *
     * @param sessionId            上下文id
     * @param userInput            用户输入
     * @param systemPromptTemplate 系统提示词
     * @return 构建好的Prompt
     */
    public Prompt build(String sessionId, String userInput, String systemPromptTemplate) {
        List<Message> history = chatMemory.get(sessionId, 5); // 获取最近5条历史
        String systemPrompt = systemPromptTemplate.replace("{TARGET_LANG}", "zh-CN");

        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage(systemPrompt));
        messages.addAll(history);
        messages.add(new UserMessage(userInput));

        return new Prompt(messages);
    }
}