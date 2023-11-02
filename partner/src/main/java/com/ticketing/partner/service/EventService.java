package com.ticketing.partner.service;


import com.ticketing.partner.dto.EventDetailsDTO;
import com.ticketing.partner.dto.EventsDTO;
import com.ticketing.partner.dto.ReserveResponseDTO;
import com.ticketing.partner.exception.EventDoesNotExistException;
import com.ticketing.partner.exception.SeatDoesNotExistException;
import com.ticketing.partner.exception.SeatIsTakenException;
import com.ticketing.partner.jsonhandler.EventItem;
import com.ticketing.partner.jsonhandler.JSONParser;
import com.ticketing.partner.jsonhandler.Seat;
import com.ticketing.partner.util.FileReader;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EventService {

    private final JSONParser jsonParser;
    private final String EVENTS_PATH = "/data/getEvents.json";
    private final String EVENT_PATH = "/data/getEvent%o.json";
    private final String RESERVE_PATH = "/data/reserve.json";

    public EventService(JSONParser parser) {
        this.jsonParser = parser;
    }

    public EventsDTO getEvents() {
        String events = readFromFile(EVENTS_PATH);
        return jsonParser.deserializeEvents(events);
    }

    public EventDetailsDTO getEvent(Long eventId) {
        EventsDTO events = getEvents();

        if (!eventExists(events, eventId)) {
            throw new EventDoesNotExistException();
        }

        String eventDetails = readFromFile(String.format(EVENT_PATH, eventId));
        return jsonParser.deserializeEventDetails(eventDetails);
    }

    private boolean eventExists(EventsDTO events, Long eventId) {
        for (EventItem eventItem : events.getData()) {
            if (eventItem.getEventId() == eventId) {
                return true;
            }
        }
        return false;
    }

    public ReserveResponseDTO reserve(Long eventId, String seatId) {
        EventsDTO events = getEvents();

        if (!eventExists(events, eventId)) {
            throw new EventDoesNotExistException();
        }

        EventDetailsDTO eventDetails = getEvent(eventId);

        seatAvailable(eventDetails, seatId);

        String reserve = readFromFile(RESERVE_PATH);
        return jsonParser.deserializeReserve(reserve);
    }

    private void seatAvailable(EventDetailsDTO eventDetails, String seatId) {
        for (Seat seat : eventDetails.getData().getSeats()) {
            if (seatId.equals(seat.getId())) {
                if (seat.isReserved()) throw new SeatIsTakenException();
                return;
            }
        }
        throw new SeatDoesNotExistException();
    }

    private String readFromFile(String path) {
        return FileReader
                .readFile(Objects.requireNonNull(getClass().getResource(path))
                        .getPath());
    }
}
