package com.piko.ticketingservice.api.dto;

public class EventDetailsDataDTO {
    private EventDetailsDTO data;

    public EventDetailsDataDTO() {
    }

    public EventDetailsDataDTO(EventDetailsDTO data) {
        this.data = data;
    }

    public EventDetailsDTO getData() {
        return data;
    }

    public void setData(EventDetailsDTO data) {
        this.data = data;
    }
}
