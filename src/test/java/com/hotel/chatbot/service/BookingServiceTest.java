package com.hotel.chatbot.service;

import com.hotel.chatbot.model.Booking;
import com.hotel.chatbot.model.Booking.BookingStatus;
import com.hotel.chatbot.model.BookingRequest;
import com.hotel.chatbot.model.Room;
import com.hotel.chatbot.model.Room.RoomType;
import com.hotel.chatbot.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link BookingService}.
 */
@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private BookingService bookingService;

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room("101", RoomType.SINGLE, 80.0, true);
        room.setId(1L);
    }

    @Test
    void createBooking_success() {
        BookingRequest request = new BookingRequest("Alice", "101", "2024-06-01", "2024-06-05");
        when(roomService.findByRoomNumber("101")).thenReturn(Optional.of(room));
        when(roomService.save(any())).thenReturn(room);

        Booking savedBooking = new Booking("Alice", room,
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 5), BookingStatus.CONFIRMED);
        savedBooking.setId(1L);
        when(bookingRepository.save(any())).thenReturn(savedBooking);

        Booking result = bookingService.createBooking(request);

        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
        assertEquals("Alice", result.getGuestName());
        assertFalse(room.isAvailable());
    }

    @Test
    void createBooking_roomNotFound_throws() {
        BookingRequest request = new BookingRequest("Alice", "999", "2024-06-01", "2024-06-05");
        when(roomService.findByRoomNumber("999")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bookingService.createBooking(request));
    }

    @Test
    void createBooking_roomUnavailable_throws() {
        room.setAvailable(false);
        BookingRequest request = new BookingRequest("Alice", "101", "2024-06-01", "2024-06-05");
        when(roomService.findByRoomNumber("101")).thenReturn(Optional.of(room));

        assertThrows(IllegalArgumentException.class, () -> bookingService.createBooking(request));
    }

    @Test
    void cancelBooking_success() {
        Booking booking = new Booking("Alice", room,
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 5), BookingStatus.CONFIRMED);
        booking.setId(1L);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(roomService.save(any())).thenReturn(room);
        when(bookingRepository.save(any())).thenReturn(booking);

        Booking result = bookingService.cancelBooking(1L);

        assertEquals(BookingStatus.CANCELLED, result.getStatus());
        assertTrue(room.isAvailable());
    }

    @Test
    void cancelBooking_notFound_throws() {
        when(bookingRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> bookingService.cancelBooking(99L));
    }

    @Test
    void getAllBookings_returnsList() {
        Booking b = new Booking("Alice", room,
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 5), BookingStatus.CONFIRMED);
        when(bookingRepository.findAll()).thenReturn(List.of(b));

        List<Booking> bookings = bookingService.getAllBookings();
        assertEquals(1, bookings.size());
    }
}
