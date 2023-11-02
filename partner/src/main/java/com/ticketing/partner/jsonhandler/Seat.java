package com.ticketing.partner.jsonhandler;

public class Seat {
    private boolean reserved;
    private int price;
    private String currency;
    private String id;

    public boolean isReserved() {
        return reserved;
    }

    public int getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public String getId() {
        return id;
    }
}
