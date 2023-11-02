package com.ticketing.partner.exception;

import com.ticketing.partner.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {EventDoesNotExistException.class})
    public ResponseEntity<?> handleException(EventDoesNotExistException exception) {
        return new ResponseEntity<>(new ErrorResponseDTO(false, 90001), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {SeatIsTakenException.class})
    public ResponseEntity<?> handleException(SeatIsTakenException exception) {
        return new ResponseEntity<>(new ErrorResponseDTO(false, 90010), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {SeatDoesNotExistException.class})
    public ResponseEntity<?> handleException(SeatDoesNotExistException exception) {
        return new ResponseEntity<>(new ErrorResponseDTO(false, 90002), HttpStatus.BAD_REQUEST);
    }
}
