package com.wedding.model;

import com.wedding.util.FileUtil;

/**
 * ============================================================================
 * ABSTRACT BASE CLASS: Guest
 * ============================================================================
 * OOP CONCEPT: ABSTRACTION & ENCAPSULATION
 * - Base class for wedding invitees.
 * - Encapsulates guestId, fullName, email, phone, and rsvpStatus.
 * - Defines abstract getType() and toFileLine() for polymorphic handling.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public abstract class Guest {
    private String guestId;
    private String fullName;
    private String email;
    private String phone;
    private String rsvpStatus; // PENDING, ACCEPTED, DECLINED

    public Guest(String guestId, String fullName, String email, String phone, String rsvpStatus) {
        this.guestId = guestId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.rsvpStatus = rsvpStatus;
    }

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

    public abstract String getType();
    public abstract String toFileLine();

    /**
     * Getter for JSP Expression Language access (${guest.guestType})
     */
    public String getGuestType() {
        return getType();
    }

    /**
     * Polymorphic method to get seating display information (${guest.seatingInfo})
     */
    public abstract String getSeatingInfo();

    protected String baseFields() {
        return guestId + "|" + FileUtil.clean(fullName) + "|" + FileUtil.clean(email)
                + "|" + FileUtil.clean(phone) + "|" + FileUtil.clean(rsvpStatus);
    }
}
