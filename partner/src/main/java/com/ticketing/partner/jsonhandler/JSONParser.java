package com.ticketing.partner.jsonhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.partner.dto.EventDetailsDTO;
import com.ticketing.partner.dto.EventsDTO;
import com.ticketing.partner.dto.ReserveResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class JSONParser {
    private final ObjectMapper mapper;


    public JSONParser(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public EventsDTO deserializeEvents(String events) {
        try {
            return mapper.readValue(events, EventsDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public EventDetailsDTO deserializeEventDetails(String eventDetails) {
        try {
            return mapper.readValue(eventDetails, EventDetailsDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ReserveResponseDTO deserializeReserve(String reserve) {
        try {
            return mapper.readValue(reserve, ReserveResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
