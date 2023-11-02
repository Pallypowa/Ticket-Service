package com.piko.ticketingservice.core.model;

import jakarta.persistence.*;

@Entity
public class UserDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    private String deviceHash;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public UserDevice(Long id, String deviceHash) {
        this.id = id;
        this.deviceHash = deviceHash;
    }

    public UserDevice() {
    }

    public String getDeviceHash() {
        return deviceHash;
    }

    public void setDeviceHash(String deviceHash) {
        this.deviceHash = deviceHash;
    }
}
