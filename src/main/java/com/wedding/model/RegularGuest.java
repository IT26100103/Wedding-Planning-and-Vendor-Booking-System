package com.wedding.model;

/**
 * ============================================================================
 * CONCRETE CLASS: RegularGuest
 * ============================================================================
 * OOP CONCEPT: INHERITANCE & POLYMORPHISM
 * - Represents standard wedding guests.
 * - Implements toFileLine(): REGULAR|guestId|fullName|email|phone|rsvpStatus
 * - Implements getSeatingInfo(): General / Open Seating
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class RegularGuest extends Guest {

    public RegularGuest(String guestId, String fullName, String email, String phone, String rsvpStatus) {
        super(guestId, fullName, email, phone, rsvpStatus);
    }

    @Override
    public String getType() { return "REGULAR"; }

    @Override
    public String getSeatingInfo() {
        return "General / Open Seating";
    }

    @Override
    public String toFileLine() {
        return "REGULAR|" + baseFields();
    }
}
