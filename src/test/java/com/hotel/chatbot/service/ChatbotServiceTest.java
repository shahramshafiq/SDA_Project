package com.hotel.chatbot.service;

import com.hotel.chatbot.model.Room;
import com.hotel.chatbot.model.Room.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ChatbotService} — intent detection and response generation.
 */
@ExtendWith(MockitoExtension.class)
class ChatbotServiceTest {

    @Mock
    private RoomService roomService;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private ChatbotService chatbotService;

    @Test
    void greeting_returnsWelcomeMessage() {
        String response = chatbotService.processMessage("Hi there!");
        assertTrue(response.toLowerCase().contains("welcome"));
    }

    @Test
    void help_returnsCommandList() {
        String response = chatbotService.processMessage("help");
        assertTrue(response.contains("available rooms"));
    }

    @Test
    void checkAvailability_returnsRoomList() {
        Room room = new Room("101", RoomType.SINGLE, 80.0, true);
        when(roomService.getAvailableRooms()).thenReturn(List.of(room));
        String response = chatbotService.processMessage("show available rooms");
        assertTrue(response.contains("101"));
    }

    @Test
    void bookRoom_invalidFormat_returnsFormatHint() {
        String response = chatbotService.processMessage("book a room please");
        assertTrue(response.toLowerCase().contains("format"));
    }

    @Test
    void cancelBooking_invalidFormat_returnsFormatHint() {
        String response = chatbotService.processMessage("cancel my reservation");
        assertTrue(response.toLowerCase().contains("format"));
    }

    @Test
    void unknownMessage_returnsDefaultResponse() {
        String response = chatbotService.processMessage("what is the weather?");
        assertTrue(response.toLowerCase().contains("didn't understand")
                || response.toLowerCase().contains("help"));
    }
}
