package com.hotel.chatbot.service;

import com.hotel.chatbot.model.Room;
import com.hotel.chatbot.model.Room.RoomType;
import com.hotel.chatbot.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RoomService}.
 */
@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    private Room availableRoom;
    private Room unavailableRoom;

    @BeforeEach
    void setUp() {
        availableRoom = new Room("101", RoomType.SINGLE, 80.0, true);
        unavailableRoom = new Room("201", RoomType.DOUBLE, 120.0, false);
    }

    @Test
    void getAllRooms_returnsAllRooms() {
        when(roomRepository.findAll()).thenReturn(List.of(availableRoom, unavailableRoom));
        List<Room> rooms = roomService.getAllRooms();
        assertEquals(2, rooms.size());
    }

    @Test
    void getAvailableRooms_returnsOnlyAvailable() {
        when(roomRepository.findByIsAvailableTrue()).thenReturn(List.of(availableRoom));
        List<Room> rooms = roomService.getAvailableRooms();
        assertEquals(1, rooms.size());
        assertTrue(rooms.get(0).isAvailable());
    }

    @Test
    void findByRoomNumber_returnsRoom_whenExists() {
        when(roomRepository.findByRoomNumber("101")).thenReturn(Optional.of(availableRoom));
        Optional<Room> result = roomService.findByRoomNumber("101");
        assertTrue(result.isPresent());
        assertEquals("101", result.get().getRoomNumber());
    }

    @Test
    void findByRoomNumber_returnsEmpty_whenNotExists() {
        when(roomRepository.findByRoomNumber("999")).thenReturn(Optional.empty());
        Optional<Room> result = roomService.findByRoomNumber("999");
        assertTrue(result.isEmpty());
    }
}
