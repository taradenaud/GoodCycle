package com.example.seg2105;

public class EventWithStatus {
    private String eventName;
    private String eventType;
    private String level;
    private String location;
    private String pace;
    private int participantLimit;
    private String registrationFee;
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
