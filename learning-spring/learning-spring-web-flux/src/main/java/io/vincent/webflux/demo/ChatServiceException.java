package io.vincent.webflux.demo;

import lombok.Getter;

@Getter
public class ChatServiceException extends RuntimeException {
    private final String sessionId;

    public ChatServiceException(String message) {
        super(message);
        this.sessionId = "$1$";
    }

    public ChatServiceException(String sessionId, String message) {
        super(sessionId + message);
        this.sessionId = sessionId;
    }

}
