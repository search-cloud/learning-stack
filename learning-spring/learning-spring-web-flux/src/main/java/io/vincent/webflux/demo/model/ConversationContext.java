package io.vincent.webflux.demo.model;

import java.util.ArrayList;
import java.util.List;

public class ConversationContext {
    private List<Message> messages = new ArrayList<>();

    public void addUserMessage(String content) {
        messages.add(new Message("user", content));
    }

    public void addAssistantMessage(String content) {
        messages.add(new Message("assistant", content));
    }

    public List<Message> getMessages() {
        return messages;
    }

    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() { return role; }
        public String getContent() { return content; }
    }
}