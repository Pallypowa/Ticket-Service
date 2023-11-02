package com.piko.ticketingservice.api.exception;

public enum ErrorCodes {
    USER_TOKEN_MISSING(10050),
    BAD_USER_TOKEN(10051),
    USER_CARD_ISSUE(10100), //Card does not belong to user
    INSUFFICIENT_FUND(10101),
    EVENT_DOES_NOT_EXIST(20001),
    SEAT_DOES_NOT_EXIST(20002),
    SEAT_ALREADY_TAKEN(20010),
    EVENT_ALREADY_STARTED(20011),
    PARTNER_SERVICE_UNAVAILABLE(20404);

    private final Integer errorCode;

    ErrorCodes(Integer error_code) {
        this.errorCode = error_code;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
