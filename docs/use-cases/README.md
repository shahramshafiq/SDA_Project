# Use Case Documentation — INNsight Hotel Booking System

**Course**: Software Design and Analysis (SDA)
**Team Members**:
- Shahram Shafiq (24i-2541)
- Sara Nasir (24i-0719)
- Taha Jabbar (24i-0816)

---

## Use Case Diagram

The file `UseCaseDiagram.drawio` contains a complete UML Use Case Diagram for the INNsight Hotel Booking System. Open it with [draw.io](https://app.diagrams.net/) (File → Open from → Device).

**System Boundary**: INNsight Hotel Booking System

**Actors**:
| Actor | Description |
|-------|-------------|
| Customer | Hotel guest who interacts with the AI chatbot |
| Staff/Admin | Hotel staff who manages rooms and bookings |
| AI Receptionist System | The automated chatbot system powered by Spring Boot |

**Key Relationships**:
- `Initiate Conversation` **<<extend>>** → `Request Information`
- `Make Room Reservation` **<<include>>** → `Provide Booking Details`
- `Modify/Cancel Reservation` **<<include>>** → `Provide Booking Details`
- `Provide Booking Details` **<<include>>** → `Process Natural Language Input`
- `Update Reservation Database` **<<include>>** → `Validate Booking Information`

---

## Use Case Index

| ID | Use Case Name | Primary Actor | File |
|----|---------------|---------------|------|
| UC-01 | Initiate Conversation | Customer | [UC01_InitiateConversation.txt](UC01_InitiateConversation.txt) |
| UC-02 | Request Information | Customer | [UC02_RequestInformation.txt](UC02_RequestInformation.txt) |
| UC-03 | Make Room Reservation | Customer | [UC03_MakeRoomReservation.txt](UC03_MakeRoomReservation.txt) |
| UC-04 | Modify/Cancel Reservation | Customer | [UC04_ModifyOrCancelReservation.txt](UC04_ModifyOrCancelReservation.txt) |
| UC-05 | Provide Booking Details | Customer | [UC05_ProvideBookingDetails.txt](UC05_ProvideBookingDetails.txt) |
| UC-06 | Process Natural Language Input | AI Receptionist System | [UC06_ProcessNaturalLanguageInput.txt](UC06_ProcessNaturalLanguageInput.txt) |
| UC-07 | Validate Booking Information | AI Receptionist System | [UC07_ValidateBookingInformation.txt](UC07_ValidateBookingInformation.txt) |
| UC-08 | Update Reservation Database | Staff/Admin | [UC08_UpdateReservationDatabase.txt](UC08_UpdateReservationDatabase.txt) |
| UC-09 | Extract Booking Information | AI Receptionist System | [UC09_ExtractBookingInformation.txt](UC09_ExtractBookingInformation.txt) |
| UC-10 | Manage Room Inventory | Staff/Admin | [UC10_ManageRoomInventory.txt](UC10_ManageRoomInventory.txt) |
| UC-11 | Interpret User Intent | AI Receptionist System | [UC11_InterpretUserIntent.txt](UC11_InterpretUserIntent.txt) |
| UC-12 | Monitor Booking Records | Staff/Admin | [UC12_MonitorBookingRecords.txt](UC12_MonitorBookingRecords.txt) |

---

## Use Case Format

Each use case file follows the **Fully Dressed** format with:
- **Use Case Name, Scope, Level, Primary Actor, Stakeholders**
- **Main Success Scenario** — two-column table (Actor Action | System Response)
- **Extensions** — alternative/error flows
- **Pre-conditions** and **Post-conditions**

The documentation references actual implementation details from the codebase (class names, method names, REST endpoints, regex patterns) to accurately reflect system behaviour.
