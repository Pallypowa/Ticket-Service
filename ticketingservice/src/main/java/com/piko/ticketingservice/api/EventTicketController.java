package com.piko.ticketingservice.api;

import com.piko.ticketingservice.api.dto.PaymentDTO;
import com.piko.ticketingservice.core.service.CoreService;
import com.piko.ticketingservice.ticket.communication.PartnerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventTicketController {
    /*/
        Call Core module from Controller...
     */

    private final CoreService coreService;
    private final PartnerClient partnerClient;

    public EventTicketController(CoreService coreService, PartnerClient partnerClient) {
        this.coreService = coreService;
        this.partnerClient = partnerClient;
    }

    @GetMapping("/getEvents")
    public ResponseEntity<?> getEvents(@RequestHeader(value = "User-Token") String userToken) {
        //In this case the event can be directly requested from the ticket module
        return new ResponseEntity<>(partnerClient.getEvents(), HttpStatus.OK);
    }

    @GetMapping("/getEvent/{eventId}")
    public ResponseEntity<?> getEvent(@RequestHeader(value = "User-Token") String userToken,
                                      @PathVariable Long eventId) {
        //In this case the event can be directly requested from the ticket module
        return new ResponseEntity<>(partnerClient.getEvent(eventId), HttpStatus.OK);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody PaymentDTO paymentDTO) {
        return new ResponseEntity<>(coreService.processPayment(paymentDTO), HttpStatus.OK);
    }
}
