package com.piko.ticketingservice.core.exception;

import com.piko.ticketingservice.api.exception.BusinessErrorException;
import com.piko.ticketingservice.api.exception.ErrorCodes;

public class BadTokenException extends BusinessErrorException {
    private final String token;

    public BadTokenException(ErrorCodes errorCodes, String token){
        super(errorCodes);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
