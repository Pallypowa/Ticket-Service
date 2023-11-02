package com.piko.ticketingservice.ticket.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piko.ticketingservice.api.dto.ErrorResponseDTO;
import com.piko.ticketingservice.api.exception.ErrorCodes;
import com.piko.ticketingservice.ticket.exception.EventDoesNotExistException;
import com.piko.ticketingservice.ticket.exception.SeatDoesNotExistException;
import com.piko.ticketingservice.ticket.exception.SeatIsTakenException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;


/*
    This class is responsible for catching all exceptions thrown by the PartnerClient's calls.
    This way, I can handle the partner's errors and return the corresponding error code to the client.
 */
public class PartnerClientErrorHandler extends DefaultResponseErrorHandler {

    public PartnerClientErrorHandler() {

    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        byte[] body = getResponseBody(response);
        String stringBody = new String(body);
        ErrorResponseDTO errorResponseDTO = mapper.readValue(stringBody, ErrorResponseDTO.class);
        switch (errorResponseDTO.getErrorCode()) {
            case 90001 -> throw new EventDoesNotExistException(ErrorCodes.EVENT_DOES_NOT_EXIST, null);
            case 90002 -> throw new SeatDoesNotExistException(ErrorCodes.SEAT_DOES_NOT_EXIST, null, null);
            case 90010 -> throw new SeatIsTakenException();
        }
    }

}


