package com.piko.ticketingservice.api.exception;

public class BusinessErrorException extends RuntimeException{
    private final ErrorCodes errorCodes;

    public BusinessErrorException(ErrorCodes errorCodes){
        this.errorCodes = errorCodes;
    }

    public ErrorCodes getErrorCodes() {
        return errorCodes;
    }
}
