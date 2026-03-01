package com.hotel.chatbot.controller;

import com.hotel.chatbot.model.ChatMessage;
import com.hotel.chatbot.service.ChatbotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * MockMvc tests for the {@link ChatController}.
 */
@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatbotService chatbotService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void chat_returnsOkWithBotResponse() throws Exception {
        when(chatbotService.processMessage(anyString()))
                .thenReturn("Hello! Welcome to the Hotel Management System.");

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("message", "hi"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sender").value("bot"))
                .andExpect(jsonPath("$.content").value("Hello! Welcome to the Hotel Management System."));
    }

    @Test
    void chat_emptyMessage_stillReturnsResponse() throws Exception {
        when(chatbotService.processMessage(anyString()))
                .thenReturn("I'm sorry, I didn't understand that.");

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("message", ""))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sender").value("bot"));
    }
}
