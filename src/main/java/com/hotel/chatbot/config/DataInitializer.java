package com.hotel.chatbot.config;

import com.hotel.chatbot.model.Room;
import com.hotel.chatbot.model.Room.RoomType;
import com.hotel.chatbot.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds the database with sample rooms on application startup.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final RoomRepository roomRepository;

    public DataInitializer(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) {
        if (roomRepository.count() == 0) {
            roomRepository.saveAll(List.of(
                    new Room("101", RoomType.SINGLE,  80.00,  true),
                    new Room("102", RoomType.SINGLE,  80.00,  true),
                    new Room("103", RoomType.SINGLE,  85.00,  true),
                    new Room("201", RoomType.DOUBLE,  120.00, true),
                    new Room("202", RoomType.DOUBLE,  120.00, true),
                    new Room("203", RoomType.DOUBLE,  130.00, true),
                    new Room("204", RoomType.DOUBLE,  130.00, true),
                    new Room("301", RoomType.SUITE,   250.00, true),
                    new Room("302", RoomType.SUITE,   275.00, true),
                    new Room("303", RoomType.SUITE,   300.00, true)
            ));
        }
    }
}
