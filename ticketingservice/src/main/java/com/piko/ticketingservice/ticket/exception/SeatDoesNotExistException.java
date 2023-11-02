package com.piko.ticketingservice.ticket.exception;

import com.piko.ticketingservice.api.exception.BusinessErrorException;
import com.piko.ticketingservice.api.exception.ErrorCodes;

public class SeatDoesNotExistException extends BusinessErrorException {
    private final String seatId;
    private final Long eventId;

    public SeatDoesNotExistException(ErrorCodes errorCode, Long eventId, String seatId){
        super(errorCode);
        this.eventId = eventId;
        this.seatId = seatId;
    }

    public String getSeatId() {
        return seatId;
    }

    public Long getEventId() {
        return eventId;
    }
}
