package com.hotel.chatbot.chatbot;

/**
 * Parses user messages using keyword matching to detect chatbot intents.
 */
public class IntentParser {

    /**
     * Determines the intent of a user message based on keyword matching.
     *
     * @param message the raw user input
     * @return the detected {@link Intent}
     */
    public Intent parse(String message) {
        if (message == null || message.isBlank()) {
            return Intent.UNKNOWN;
        }
        String lower = message.toLowerCase();

        if (lower.contains("hello") || lower.contains("hi") || lower.contains("hey")) {
            return Intent.GREETING;
        }
        if (lower.contains("help")) {
            return Intent.HELP;
        }
        if (lower.contains("cancel")) {
            return Intent.CANCEL_BOOKING;
        }
        if (lower.contains("my booking") || lower.contains("list booking")
                || lower.contains("show booking") || lower.contains("all booking")) {
            return Intent.LIST_BOOKINGS;
        }
        if (lower.contains("book") || lower.contains("reserve")) {
            return Intent.BOOK_ROOM;
        }
        if (lower.contains("available") || lower.contains("room") || lower.contains("check")) {
            return Intent.CHECK_AVAILABILITY;
        }
        return Intent.UNKNOWN;
    }
}
