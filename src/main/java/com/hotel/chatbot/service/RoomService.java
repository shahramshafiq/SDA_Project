package com.hotel.chatbot.service;

import com.hotel.chatbot.model.Room;
import com.hotel.chatbot.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class containing business logic for room management.
 */
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Returns all rooms in the system.
     *
     * @return list of all rooms
     */
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    /**
     * Returns all rooms that are currently available.
     *
     * @return list of available rooms
     */
    public List<Room> getAvailableRooms() {
        return roomRepository.findByIsAvailableTrue();
    }

    /**
     * Finds a room by its room number.
     *
     * @param roomNumber the room number string
     * @return an Optional containing the room if found
     */
    public Optional<Room> findByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

    /**
     * Saves a room entity.
     *
     * @param room the room to save
     * @return the saved room
     */
    public Room save(Room room) {
        return roomRepository.save(room);
    }
}
