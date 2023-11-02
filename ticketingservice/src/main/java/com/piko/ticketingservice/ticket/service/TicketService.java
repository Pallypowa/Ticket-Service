package com.piko.ticketingservice.ticket.service;

import com.piko.ticketingservice.api.dto.*;
import com.piko.ticketingservice.ticket.communication.PartnerClient;
import com.piko.ticketingservice.ticket.exception.EventAlreadyStartedException;
import com.piko.ticketingservice.ticket.exception.EventDoesNotExistException;
import com.piko.ticketingservice.ticket.exception.SeatDoesNotExistException;
import com.piko.ticketingservice.ticket.exception.SeatIsTakenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

/*
    This class is responsible for handling ticket related business logic.
    Checks whether the event exists, seat is taken, seat exists etc...
    Does not have any internal database, it does the logic by communicating with the partner service.
 */
@Service
public class TicketService {
    private final PartnerClient partnerClient;
    private final Logger logger = LoggerFactory.getLogger(TicketService.class);

    public TicketService(PartnerClient partnerClient) {
        this.partnerClient = partnerClient;
    }

    public PaymentResponseDTO reserveSeat(ReserveDTO reserveDTO) {
        logger.trace("Seat reservation started. Event id: " + reserveDTO.getEventId() + " seat id: " + reserveDTO.getSeatId());
        Long eventId = reserveDTO.getEventId();
        String seatId = reserveDTO.getSeatId();

        //Event exists? Has it already started?
        checkEventValidity(eventId);

        //Seat exists? Is it taken?
        checkSeatValidity(eventId, seatId);


        logger.trace("Seat reservation successful");
        //All checks passed... proceed with reservation.
        return partnerClient.reserveSeat(reserveDTO);
    }

    public EventDetailsDataDTO getEvent(Long eventId) {
        EventsDTO events = partnerClient.getEvents();

        if (!doesEventExist(eventId, events)) {
            throw new EventDoesNotExistException();
        }

        return partnerClient.getEvent(eventId);
    }

    private void checkEventValidity(Long eventId) {
        EventsDTO events = partnerClient.getEvents();

        if (!doesEventExist(eventId, events)) {
            throw new EventDoesNotExistException();
        }

        Optional<EventItem> eventItem = events
                .getData()
                .stream()
                .filter(e -> e.getEventId() == eventId)
                .findFirst();

        if (hasEventStarted(eventItem.get().getStartTimeStamp())) {
            throw new EventAlreadyStartedException();
        }
    }

    private boolean hasEventStarted(String startTimeStamp) {
        long currentTime = Instant.now().getEpochSecond();
        long eventTime = Long.parseLong(startTimeStamp);

        return currentTime >= eventTime;
    }

    private void checkSeatValidity(Long eventId, String seatId) {
        //Check if seat is occupied
        EventDetailsDataDTO eventDetails = partnerClient.getEvent(eventId);

        //First we find if there is a seat for the event with the same seat id
        Optional<Seat> seat = eventDetails
                .getData()
                .getSeats()
                .stream()
                .filter(s -> s.getId().equals(seatId))
                .findFirst();

        if (seat.isEmpty()) {
            throw new SeatDoesNotExistException();
        }

        if (seat.get().isReserved()) {
            throw new SeatIsTakenException();
        }
    }

    private boolean doesEventExist(Long eventId, EventsDTO events) {
        return events.getData()
                .stream()
                .anyMatch(e -> e.getEventId() == eventId);
    }

}
