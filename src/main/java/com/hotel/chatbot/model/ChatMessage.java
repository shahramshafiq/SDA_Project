package com.hotel.chatbot.model;

import java.time.LocalDateTime;

/**
 * DTO representing a single chat message exchanged in the chatbot interface.
 */
public class ChatMessage {

    private String sender;
    private String content;
    private LocalDateTime timestamp;

    public ChatMessage() {}

    public ChatMessage(String sender, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
