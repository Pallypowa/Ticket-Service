package com.piko.ticketingservice.ticket.exception;

import com.piko.ticketingservice.api.exception.BusinessErrorException;
import com.piko.ticketingservice.api.exception.ErrorCodes;

import java.util.Date;

public class EventAlreadyStartedException extends BusinessErrorException {
    private final Date eventStartDate;

    public EventAlreadyStartedException(ErrorCodes errorCode, String eventStartDate){
        super(errorCode);
        this.eventStartDate = new Date(Long.parseLong(eventStartDate)*1000);
    }

    public Date getEventStartDate() {
        return eventStartDate;
    }
}
