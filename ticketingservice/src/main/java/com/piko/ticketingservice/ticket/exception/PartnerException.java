package com.piko.ticketingservice.ticket.exception;

import com.piko.ticketingservice.api.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatusCode;

public class PartnerException extends Throwable {
    private HttpStatusCode statusCode;
    private ErrorResponseDTO errorResponse;

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public ErrorResponseDTO getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponseDTO errorResponse) {
        this.errorResponse = errorResponse;
    }

    public PartnerException(HttpStatusCode statusCode, ErrorResponseDTO errorResponse) {
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
    }
}
