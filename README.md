# Hotel Management AI Chatbot

A **Software Design and Analysis (SDA)** course project — an AI-powered hotel management chatbot built with Java and Spring Boot.

## Team Members

| Name | Student ID |
|------|-----------|
| Shahram Shafiq | 24i-2541 |
| Sara Nasir | 24i-0719 |
| Taha Jabbar | 24i-0816 |

## Technology Stack

- **Java 17** — primary language
- **Spring Boot 3.x** — web framework
- **Maven** — build management
- **H2** — in-memory database (no external DB setup required)
- **Spring Data JPA** — persistence layer
- **Spring Web** — REST API
- **HTML / CSS / JavaScript** — simple chat web UI (served as static resources)

## Project Structure

```
SDA_Project/
├── pom.xml
├── .gitignore
├── README.md
├── docs/
│   └── ShahramShafiq-24i2541_SaraNasir-24i0719_TahaJabbar-24i0816_F.pdf
└── src/
    ├── main/
    │   ├── java/com/hotel/chatbot/
    │   │   ├── HotelChatbotApplication.java
    │   │   ├── config/DataInitializer.java
    │   │   ├── model/          (Room, Booking, ChatMessage, BookingRequest)
    │   │   ├── repository/     (RoomRepository, BookingRepository)
    │   │   ├── service/        (RoomService, BookingService, ChatbotService)
    │   │   ├── controller/     (ChatController, RoomController, BookingController)
    │   │   └── chatbot/        (Intent enum, IntentParser)
    │   └── resources/
    │       ├── application.properties
    │       └── static/         (index.html, css/style.css, js/chat.js)
    └── test/
        └── java/com/hotel/chatbot/
            ├── HotelChatbotApplicationTests.java
            ├── service/        (RoomServiceTest, BookingServiceTest, ChatbotServiceTest)
            └── controller/     (ChatControllerTest)
```

## Build & Run

**Prerequisites:** Java 17+, Maven 3.6+

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

Then open **http://localhost:8080** in your browser to use the chat UI.

The H2 console is available at **http://localhost:8080/h2-console**  
(JDBC URL: `jdbc:h2:mem:hoteldb`, username: `sa`, no password).

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/chat` | Send a chat message, receive bot response |
| `GET` | `/api/rooms` | List all rooms |
| `GET` | `/api/rooms/available` | List available rooms only |
| `POST` | `/api/bookings` | Create a new booking |
| `GET` | `/api/bookings` | List all bookings |
| `DELETE` | `/api/bookings/{id}` | Cancel a booking |

### Chat API Example

**Request:**
```json
POST /api/chat
{ "message": "show available rooms" }
```

**Response:**
```json
{
  "sender": "bot",
  "content": "Here are the available rooms:\n  • Room 101 (SINGLE) — $80.00/night\n  ...",
  "timestamp": "2024-03-01T10:00:00"
}
```

## Features

- **Room Management** — 10 pre-seeded rooms (SINGLE, DOUBLE, SUITE); list and filter by availability
- **Booking Management** — create, list, and cancel bookings with automatic room availability updates
- **AI Chatbot Engine** — rule-based intent detection; supports natural language queries
- **Web Chat UI** — clean hotel-themed chat interface served at `/`

### Supported Chatbot Commands

| Input example | Intent |
|---------------|--------|
| "hi" / "hello" | GREETING |
| "help" | HELP |
| "show available rooms" | CHECK_AVAILABILITY |
| "book room 101 for Alice from 2024-06-01 to 2024-06-05" | BOOK_ROOM |
| "cancel booking 1" | CANCEL_BOOKING |
| "show my bookings" | LIST_BOOKINGS |

## Design Patterns Used

- **MVC** — Controllers, Services, Repositories separated by concern
- **Repository Pattern** — Spring Data JPA repositories abstract persistence
- **Service Layer** — Business logic isolated in service classes
- **DTO Pattern** — `ChatMessage` and `BookingRequest` as data transfer objects

## Future Enhancements

- Integration with an LLM API (OpenAI / Gemini) for natural language understanding
- Persistent PostgreSQL / MySQL database
- JWT-based authentication and user accounts
- Admin dashboard for hotel staff
- Email confirmation for bookings
