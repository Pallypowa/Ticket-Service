package com.piko.ticketingservice.api.exception;

import com.piko.ticketingservice.api.dto.ErrorResponseDTO;
import com.piko.ticketingservice.core.exception.BadCardException;
import com.piko.ticketingservice.core.exception.BadTokenException;
import com.piko.ticketingservice.core.exception.InsufficientFundsException;
import com.piko.ticketingservice.ticket.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

/*
    This class is responsible for handling the business exceptions thrown in the core/ticket module to return the correct payload, http code.
 */

@ControllerAdvice
public class RestControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> handleMissingRequestExc() {
        logger.error("A felhasználói token nem szerepel");
        return new ResponseEntity<>(
                new ErrorResponseDTO(false,
                        ErrorCodes.USER_TOKEN_MISSING.getErrorCode()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadTokenException.class)
    public ResponseEntity<?> handleBadTokenExc(BadTokenException exception) {
        logger.error("A beérkezett kérésben szereplő felhasználó token lejárt vagy nem értelmezhető: "
                + exception.getToken(), exception);
        return new ResponseEntity<>(
                new ErrorResponseDTO(false,
                        exception.getErrorCodes().getErrorCode()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCardException.class)
    public ResponseEntity<?> handleBadCardExc(BadCardException exception) {
        logger.error("Ez a bankkártya: "
                +  exception.getCardId()
                + " nem ehhez a felhasználóhoz tartozik: "
                + exception.getEventId(), exception);
        return new ResponseEntity<>(
                new ErrorResponseDTO(false,
                        exception.getErrorCodes().getErrorCode()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventAlreadyStartedException.class)
    public ResponseEntity<?> handleEventStartedExc(EventAlreadyStartedException exception) {
        logger.error("Olyan eseményre ami már elkezdődött nem lehet jegyet eladni! Esemény időpontja: "
                + exception.getEventStartDate(), exception);
        return new ResponseEntity<>(
                new ErrorResponseDTO(false,
                        exception.getErrorCodes().getErrorCode()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventDoesNotExistException.class)
    public ResponseEntity<?> handleEventNotExistExc(EventDoesNotExistException exception) {
        logger.error("Nem létezik ilyen esemény! Esemény id: " + exception.getEventId(), exception);
        return new ResponseEntity<>(
                new ErrorResponseDTO(false,
                        exception.getErrorCodes().getErrorCode()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeatDoesNotExistException.class)
    public ResponseEntity<?> handleSeatNotExistExc(SeatDoesNotExistException exception) {
        logger.error("Nem létezik ilyen szék!");
        return new ResponseEntity<>(
                new ErrorResponseDTO(false,
                        ErrorCodes.SEAT_DOES_NOT_EXIST.getErrorCode()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> handleInsufficientFundExc() {
        logger.error("A felhasználónak nincs elegendő pénze hogy megvásárolja a jegyet!");
        return new ResponseEntity<>(
                new ErrorResponseDTO(false,
                        ErrorCodes.INSUFFICIENT_FUND.getErrorCode()),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SeatIsTakenException.class)
    public ResponseEntity<?> handleSeatTakenExc() {
        logger.error("Már lefoglalt székre nem lehet jegyet eladni!");
        return new ResponseEntity<>(
                new ErrorResponseDTO(false,
                        ErrorCodes.SEAT_ALREADY_TAKEN.getErrorCode()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<?> handlePartnerNotAvailable() {
        logger.error("A külső rendszer nem elérhető!");
        return new ResponseEntity<>(
                new ErrorResponseDTO(false,
                        ErrorCodes.PARTNER_SERVICE_UNAVAILABLE.getErrorCode()),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(PartnerException.class)
    public ResponseEntity<?> handlePartnerExceptions(PartnerException partnerException) {
        switch (partnerException.getErrorResponse().getErrorCode()) {
            case 90001 -> logger.error("Nem létezik ilyen esemény!");
            case 90002 -> logger.error("Nem létezik ilyen szék!");
            case 90010 -> logger.error("Már lefoglalt székre nem lehet jegyet eladni!");
        }
        return new ResponseEntity<>(
                partnerException.getErrorResponse(),
                HttpStatus.BAD_REQUEST);
    }
}
