package com.wedding.model;

public abstract class Guest {
    private String guestId;
    private String fullName;
    private String email;
    private String phone;
    private String rsvpStatus;   // PENDING, ACCEPTED or DECLINED

    public Guest(String guestId, String fullName, String email, String phone, String rsvpStatus) {
        this.guestId = guestId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.rsvpStatus = rsvpStatus;
    }

    // Getters and setters: this is encapsulation
    public String getGuestId() { return guestId; }
    public void setGuestId(String guestId) { this.guestId = guestId; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getRsvpStatus() { return rsvpStatus; }
    public void setRsvpStatus(String rsvpStatus) { this.rsvpStatus = rsvpStatus; }

    // Abstract methods: every child class must write its own version
    public abstract String getGuestType();
    public abstract String getSeatingInfo();

    // Turns this object into one line of text for guests.txt
    public String toFileLine() {
        return getGuestType() + "|" + guestId + "|" + fullName + "|" + email + "|" + phone + "|" + rsvpStatus;
    }
}
