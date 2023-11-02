package com.ticketing.partner.dto;

public class ReserveDTO {
    private Long eventId;
    private String seatId;

    public ReserveDTO(Long eventId, String seatId) {
        this.eventId = eventId;
        this.seatId = seatId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
}
