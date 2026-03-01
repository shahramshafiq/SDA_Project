package com.hotel.chatbot.service;

import com.hotel.chatbot.chatbot.Intent;
import com.hotel.chatbot.chatbot.IntentParser;
import com.hotel.chatbot.model.Booking;
import com.hotel.chatbot.model.BookingRequest;
import com.hotel.chatbot.model.Room;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service that drives the chatbot: detects user intent and generates
 * natural language responses by delegating to domain services.
 */
@Service
public class ChatbotService {

    private static final Pattern BOOK_PATTERN = Pattern.compile(
            "(?:book|reserve)\\s+room\\s+(\\S+)\\s+for\\s+(.+?)\\s+from\\s+(\\S+)\\s+to\\s+(\\S+)",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern CANCEL_PATTERN = Pattern.compile(
            "cancel\\s+(?:booking\\s+)?(\\d+)",
            Pattern.CASE_INSENSITIVE
    );

    private final IntentParser intentParser = new IntentParser();
    private final RoomService roomService;
    private final BookingService bookingService;

    public ChatbotService(RoomService roomService, BookingService bookingService) {
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    /**
     * Processes a user message and returns an appropriate chatbot response.
     *
     * @param message the raw user input
     * @return the chatbot's natural language response
     */
    public String processMessage(String message) {
        Intent intent = intentParser.parse(message);

        return switch (intent) {
            case GREETING -> handleGreeting();
            case HELP -> handleHelp();
            case CHECK_AVAILABILITY -> handleCheckAvailability();
            case BOOK_ROOM -> handleBookRoom(message);
            case CANCEL_BOOKING -> handleCancelBooking(message);
            case LIST_BOOKINGS -> handleListBookings();
            default -> "I'm sorry, I didn't understand that. Type 'help' for available commands.";
        };
    }

    private String handleGreeting() {
        return "Hello! Welcome to the Hotel Management System. How can I help you today? "
                + "Type 'help' to see available commands.";
    }

    private String handleHelp() {
        return """
                Available commands:
                • "show available rooms" — list all available rooms
                • "book room <number> for <name> from <YYYY-MM-DD> to <YYYY-MM-DD>" — make a booking
                • "cancel booking <id>" — cancel an existing booking
                • "show my bookings" / "list bookings" — view all bookings
                • "help" — show this help message
                """;
    }

    private String handleCheckAvailability() {
        List<Room> available = roomService.getAvailableRooms();
        if (available.isEmpty()) {
            return "Sorry, there are no available rooms at the moment.";
        }
        StringBuilder sb = new StringBuilder("Here are the available rooms:\n");
        for (Room r : available) {
            sb.append(String.format("  • Room %s (%s) — $%.2f/night%n",
                    r.getRoomNumber(), r.getType(), r.getPricePerNight()));
        }
        return sb.toString().trim();
    }

    private String handleBookRoom(String message) {
        Matcher m = BOOK_PATTERN.matcher(message);
        if (!m.find()) {
            return "To book a room, please use the format: "
                    + "\"book room <number> for <guest name> from <YYYY-MM-DD> to <YYYY-MM-DD>\"";
        }
        try {
            BookingRequest request = new BookingRequest(
                    m.group(2).trim(), m.group(1).trim(), m.group(3).trim(), m.group(4).trim());
            Booking booking = bookingService.createBooking(request);
            return String.format(
                    "Booking confirmed! Booking #%d — Room %s for %s. Check-in: %s, Check-out: %s.",
                    booking.getId(),
                    booking.getRoom().getRoomNumber(),
                    booking.getGuestName(),
                    booking.getCheckInDate(),
                    booking.getCheckOutDate()
            );
        } catch (IllegalArgumentException e) {
            return "Booking failed: " + e.getMessage();
        }
    }

    private String handleCancelBooking(String message) {
        Matcher m = CANCEL_PATTERN.matcher(message);
        if (!m.find()) {
            return "To cancel a booking, please use the format: \"cancel booking <id>\"";
        }
        try {
            long bookingId = Long.parseLong(m.group(1));
            bookingService.cancelBooking(bookingId);
            return "Booking #" + bookingId + " has been cancelled successfully.";
        } catch (IllegalArgumentException e) {
            return "Cancellation failed: " + e.getMessage();
        }
    }

    private String handleListBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            return "There are no bookings in the system.";
        }
        StringBuilder sb = new StringBuilder("All bookings:\n");
        for (Booking b : bookings) {
            sb.append(String.format("  • #%d — Room %s, Guest: %s, %s to %s [%s]%n",
                    b.getId(),
                    b.getRoom().getRoomNumber(),
                    b.getGuestName(),
                    b.getCheckInDate(),
                    b.getCheckOutDate(),
                    b.getStatus()
            ));
        }
        return sb.toString().trim();
    }
}
