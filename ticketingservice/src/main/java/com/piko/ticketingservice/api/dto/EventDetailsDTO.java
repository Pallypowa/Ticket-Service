package com.piko.ticketingservice.api.dto;

import java.util.List;

public class EventDetailsDTO {
    private int eventId;
    private List<Seat> seats;

    public EventDetailsDTO() {
    }

    public EventDetailsDTO(int eventId, List<Seat> seats) {
        this.eventId = eventId;
        this.seats = seats;
    }

    public int getEventId() {
        return eventId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
