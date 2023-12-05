package com.example.seg2105;

public class EventWithStatus {
    private String eventName;
    private String userStatus; // User's registration status

    public EventWithStatus(String eventName, String userStatus) {
        this.eventName = eventName;
        this.userStatus = userStatus;
    }

    // Getters
    public String getEventName() {
        return eventName;
    }
    public String getUserStatus() {
        return userStatus;
    }
}
