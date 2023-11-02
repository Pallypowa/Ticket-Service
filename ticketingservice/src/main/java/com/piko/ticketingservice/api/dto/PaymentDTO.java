package com.piko.ticketingservice.api.dto;

public class PaymentDTO {
    private Long eventId;
    private String seatId;
    private String cardId;


    public PaymentDTO(Long eventId, String seatId, String cardId) {
        this.eventId = eventId;
        this.seatId = seatId;
        this.cardId = cardId;
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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
