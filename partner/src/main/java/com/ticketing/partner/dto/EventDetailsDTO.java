package com.ticketing.partner.dto;

import com.ticketing.partner.jsonhandler.Seat;

import java.util.List;

public class EventDetailsDTO {
    private EventDetailsDTO data;
    private boolean success;
    private int eventId;
    private List<Seat> seats;

    public EventDetailsDTO() {
    }

    public EventDetailsDTO getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getEventId() {
        return eventId;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
