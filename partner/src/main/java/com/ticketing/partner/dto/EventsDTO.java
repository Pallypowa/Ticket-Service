package com.ticketing.partner.dto;

import com.ticketing.partner.jsonhandler.EventItem;

import java.util.List;

public class EventsDTO {
    private List<EventItem> data;
    private boolean success;

    public List<EventItem> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public EventsDTO(List<EventItem> data, boolean success) {
        this.data = data;
        this.success = success;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data=" + data +
                ", success=" + success +
                '}';
    }
}