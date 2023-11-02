package com.piko.ticketingservice.ticket.exception;

import com.piko.ticketingservice.api.exception.BusinessErrorException;
import com.piko.ticketingservice.api.exception.ErrorCodes;

public class EventDoesNotExistException extends BusinessErrorException {
    private final Long eventId;

    public EventDoesNotExistException(ErrorCodes errorCode, Long eventId){
        super(errorCode);
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }
}
