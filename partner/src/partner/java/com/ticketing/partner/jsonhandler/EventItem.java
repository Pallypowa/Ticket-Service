package com.ticketing.partner.jsonhandler;

public class EventItem {
    private int eventId;
    private String startTimeStamp;
    private String endTimeStamp;
    private String location;
    private String title;

    public int getEventId() {
        return eventId;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public EventItem(int eventId, String startTimeStamp, String endTimeStamp, String location, String title) {
        this.eventId = eventId;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.location = location;
        this.title = title;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "eventId=" + eventId +
                ", startTimeStamp='" + startTimeStamp + '\'' +
                ", endTimeStamp='" + endTimeStamp + '\'' +
                ", location='" + location + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
