package com.piko.ticketingservice.api;

import com.piko.ticketingservice.api.dto.PaymentDTO;
import com.piko.ticketingservice.core.service.CoreService;
import com.piko.ticketingservice.ticket.service.TicketService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventTicketController {
    /*/
        Call Core module from Controller...
     */

    private final CoreService coreService;
    private final TicketService ticketService;

    public EventTicketController(CoreService coreService, TicketService ticketService) {
        this.coreService = coreService;
        this.ticketService = ticketService;
    }

    @GetMapping("/getEvents")
    public ResponseEntity<?> getEvents() {
        //In this case the event can be directly requested from the ticket module, because we don't need the core service's user logic
        return new ResponseEntity<>(ticketService.getEvents(), HttpStatus.OK);
    }

    @GetMapping("/getEvent/{eventId}")
    public ResponseEntity<?> getEvent(@RequestHeader(value = "User-Token") String userToken,
                                      @NotNull @PathVariable Long eventId) {
        //In this case the event can be directly requested from the ticket module, because we don't need the core service's user logic
        return new ResponseEntity<>(ticketService.getEvent(eventId), HttpStatus.OK);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@NotNull @RequestBody PaymentDTO paymentDTO) {
        return new ResponseEntity<>(coreService.processPayment(paymentDTO), HttpStatus.OK);
    }
}
