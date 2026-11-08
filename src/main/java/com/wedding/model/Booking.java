package com.wedding.model;

import com.wedding.util.FileUtil;

public abstract class Booking {
    private String id;
    private String userId;
    private String resourceId;     // vendor id or venue id
    private String eventDate;      // yyyy-MM-ddgit --version
    private String eventTime;      // e.g. 10:00
    private int guests;
    private String status;         // PENDING, CONFIRMED, CANCELLED

    public Booking(String id, String userId, String resourceId, String eventDate,
                   String eventTime, int guests, String status) {
        this.id = id;
        this.userId = userId;
        this.resourceId = resourceId;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.guests = guests;
        this.status = status;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getResourceId() { return resourceId; }
    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }
    public String getEventTime() { return eventTime; }
    public void setEventTime(String eventTime) { this.eventTime = eventTime; }
    public int getGuests() { return guests; }
    public void setGuests(int guests) { this.guests = guests; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Each child class writes its own version
    public abstract String getBookingType();
    public abstract double calculateTotal();
    public abstract String toFileString();

    public void display() {
        System.out.println("[" + getBookingType() + "] " + id + " | User: " + userId
                + " | " + resourceId + " | " + eventDate + " " + eventTime
                + " | Guests: " + guests + " | " + status + " | Total: " + calculateTotal());
    }

    protected String baseFields() {
        return id + "|" + userId + "|" + resourceId + "|" + eventDate + "|" + eventTime
                + "|" + guests + "|" + status;
    }
}
