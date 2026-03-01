package com.hotel.chatbot.controller;

import com.hotel.chatbot.model.ChatMessage;
import com.hotel.chatbot.service.ChatbotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * REST controller for the chatbot endpoint.
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatbotService chatbotService;

    public ChatController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    /**
     * Accepts a user message and returns the bot's response.
     *
     * @param payload map containing a "message" key
     * @return a {@link ChatMessage} with the bot's reply
     */
    @PostMapping
    public ResponseEntity<ChatMessage> chat(@RequestBody Map<String, String> payload) {
        String userMessage = payload.getOrDefault("message", "");
        String response = chatbotService.processMessage(userMessage);
        ChatMessage reply = new ChatMessage("bot", response, LocalDateTime.now());
        return ResponseEntity.ok(reply);
    }
}
