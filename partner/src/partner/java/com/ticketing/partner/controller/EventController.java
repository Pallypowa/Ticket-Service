package com.ticketing.partner.controller;

import com.ticketing.partner.dto.ReserveDTO;
import com.ticketing.partner.service.EventService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/getEvents")
    public ResponseEntity<?> getEvents() throws InterruptedException {
        return new ResponseEntity<>(eventService.getEvents(), HttpStatus.OK);
    }

    @GetMapping("/getEvent/{eventId}")
    public ResponseEntity<?> getEvent(@NotNull @PathVariable Long eventId) {
        return new ResponseEntity<>(eventService.getEvent(eventId), HttpStatus.OK);
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> reserve(@NotNull @RequestBody ReserveDTO reserveDTO) {
        return new ResponseEntity<>(eventService.reserve(reserveDTO.getEventId(),
                reserveDTO.getSeatId()),
                HttpStatus.OK);
    }
}