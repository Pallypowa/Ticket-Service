package com.piko.ticketingservice.ticket.communication;

import com.piko.ticketingservice.api.dto.EventDetailsDataDTO;
import com.piko.ticketingservice.api.dto.EventsDTO;
import com.piko.ticketingservice.api.dto.PaymentResponseDTO;
import com.piko.ticketingservice.api.dto.ReserveDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*
    This class is responsible for the communication with the "3rd party" partner service.
    Doesn't do any business logic, as the ticket->service layer is responsible for that.
 */
@Component
public class PartnerClient {
    private final RestTemplate restTemplate;

    public PartnerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EventsDTO getEvents() {
        String GET_EVENTS = "/getEvents";

        ResponseEntity<EventsDTO> response = restTemplate.getForEntity(GET_EVENTS, EventsDTO.class);
        return response.getBody();
    }

    public EventDetailsDataDTO getEvent(Long eventId) {
        String GET_EVENT = "/getEvent/%o";

        ResponseEntity<EventDetailsDataDTO> response = restTemplate.getForEntity(String.format(GET_EVENT, eventId), EventDetailsDataDTO.class);
        return response.getBody();
    }

    public PaymentResponseDTO reserveSeat(ReserveDTO reserveDTO) {
        String RESERVE_SEAT = "/reserve";

        return restTemplate.postForEntity(RESERVE_SEAT, reserveDTO, PaymentResponseDTO.class).getBody();
    }

}
