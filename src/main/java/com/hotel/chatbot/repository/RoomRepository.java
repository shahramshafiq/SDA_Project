package com.hotel.chatbot.repository;

import com.hotel.chatbot.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for {@link Room} entities.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByIsAvailableTrue();

    Optional<Room> findByRoomNumber(String roomNumber);
}
