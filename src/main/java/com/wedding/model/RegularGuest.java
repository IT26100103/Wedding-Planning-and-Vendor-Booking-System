package com.wedding.model;

public class RegularGuest extends Guest {

    public RegularGuest(String guestId, String fullName, String email, String phone, String rsvpStatus) {
        super(guestId, fullName, email, phone, rsvpStatus);
    }

    @Override
    public String getGuestType() {
        return "REGULAR";
    }

    @Override
    public String getSeatingInfo() {
        return "General seating";
    }
}
