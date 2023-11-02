package com.piko.ticketingservice.core.exception;

import com.piko.ticketingservice.api.exception.BusinessErrorException;
import com.piko.ticketingservice.api.exception.ErrorCodes;

//Card does not belong to user exception
public class BadCardException extends BusinessErrorException {
    private final String cardId;
    private final Long eventId;

    public BadCardException(ErrorCodes errorCode, String cardId, Long eventId) {
        super(errorCode);
        this.cardId = cardId;
        this.eventId = eventId;
    }

    public String getCardId() {
        return cardId;
    }

    public Long getEventId() {
        return eventId;
    }
}
