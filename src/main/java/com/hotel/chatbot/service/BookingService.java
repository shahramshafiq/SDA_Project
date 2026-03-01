package com.hotel.chatbot.service;

import com.hotel.chatbot.model.Booking;
import com.hotel.chatbot.model.BookingRequest;
import com.hotel.chatbot.model.Room;
import com.hotel.chatbot.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class containing business logic for booking management.
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    public BookingService(BookingRepository bookingRepository, RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
    }

    /**
     * Creates a new booking for a guest.
     *
     * @param request the booking request DTO
     * @return the created {@link Booking}
     * @throws IllegalArgumentException if the room is not found or not available
     */
    @Transactional
    public Booking createBooking(BookingRequest request) {
        Room room = roomService.findByRoomNumber(request.getRoomNumber())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Room " + request.getRoomNumber() + " not found."));

        if (!room.isAvailable()) {
            throw new IllegalArgumentException(
                    "Room " + request.getRoomNumber() + " is not available.");
        }

        room.setAvailable(false);
        roomService.save(room);

        Booking booking = new Booking(
                request.getGuestName(),
                room,
                LocalDate.parse(request.getCheckInDate()),
                LocalDate.parse(request.getCheckOutDate()),
                Booking.BookingStatus.CONFIRMED
        );
        return bookingRepository.save(booking);
    }

    /**
     * Cancels an existing booking by ID.
     *
     * @param bookingId the ID of the booking to cancel
     * @return the cancelled {@link Booking}
     * @throws IllegalArgumentException if the booking is not found
     */
    @Transactional
    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Booking #" + bookingId + " not found."));

        if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Booking #" + bookingId + " is already cancelled.");
        }

        booking.setStatus(Booking.BookingStatus.CANCELLED);
        booking.getRoom().setAvailable(true);
        roomService.save(booking.getRoom());
        return bookingRepository.save(booking);
    }

    /**
     * Returns all bookings in the system.
     *
     * @return list of all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Finds a booking by ID.
     *
     * @param bookingId the booking ID
     * @return an Optional containing the booking if found
     */
    public Optional<Booking> findById(Long bookingId) {
        return bookingRepository.findById(bookingId);
    }
}
