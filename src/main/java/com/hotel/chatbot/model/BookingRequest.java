package com.hotel.chatbot.model;

/**
 * DTO for incoming booking requests via the chatbot or REST API.
 */
public class BookingRequest {

    private String guestName;
    private String roomNumber;
    private String checkInDate;
    private String checkOutDate;

    public BookingRequest() {}

    public BookingRequest(String guestName, String roomNumber,
                          String checkInDate, String checkOutDate) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getCheckInDate() { return checkInDate; }
    public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }

    public String getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }
}
