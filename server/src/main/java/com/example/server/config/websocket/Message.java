package com.example.server.config.websocket;

import java.time.LocalDateTime;

public class Message {
    private String from;
    private String message;
    private LocalDateTime timestamp;

    public Message() {
        // required for Jackson
    }

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
    }

    public Message(String from, String message, LocalDateTime timestamp) {
        this.from = from;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }
}
