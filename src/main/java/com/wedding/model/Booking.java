package com.wedding.model;

import com.wedding.util.FileUtil;

/**
 * ============================================================================
 * ABSTRACT BASE CLASS: Booking
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. ABSTRACTION:
 *    - Encapsulates common reservation data: booking ID, client user ID,
 *      resource ID (vendor or venue), event date and time, guest count,
 *      and reservation status (PENDING, CONFIRMED, CANCELLED).
 *    - Defines abstract operations calculateTotal(), getBookingType(), and toFileString().
 * 2. ENCAPSULATION:
 *    - Private attributes protected by getters and setters.
 * 3. POLYMORPHISM:
 *    - Subclasses (VendorBooking and VenueBooking) dynamically delegate total cost
 *      calculation to the respective booked resource (Vendor vs Venue).
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public abstract class Booking {
    /** Unique reservation record identifier (e.g., B001) */
    private String id;

    /** ID of the client user who made the booking */
    private String userId;

    /** ID of the reserved resource (vendorId or venueId) */
    private String resourceId;

    /** Date of the wedding event (format: yyyy-MM-dd) */
    private String eventDate;

    /** Scheduled event time (e.g., "10:00 AM", "18:30") */
    private String eventTime;

    /** Estimated count of attending guests for this booking */
    private int guests;

    /** Current reservation status: PENDING, CONFIRMED, or CANCELLED */
    private String status;

    /**
     * Parameterized constructor for booking records.
     *
     * @param id         Unique booking identifier
     * @param userId     Client user ID
     * @param resourceId Target vendor or venue ID
     * @param eventDate  Event date (yyyy-MM-dd)
     * @param eventTime  Event start time
     * @param guests     Number of guests
     * @param status     Reservation status
     */
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

    /**
     * Polymorphic method: Identifies booking classification ("VENDOR" or "VENUE").
     *
     * @return Booking classification string
     */
    public abstract String getBookingType();

    /**
     * Polymorphic method: Computes total reservation cost by querying the associated
     * resource and invoking its specific pricing calculation formula.
     *
     * @return Total monetary cost of reservation
     */
    public abstract double calculateTotal();

    /**
     * Polymorphic method: Formats the booking record for persistent file storage.
     *
     * @return Pipe-delimited record for bookings.txt
     */
    public abstract String toFileString();

    /**
     * Diagnostic console display method.
     */
    public void display() {
        System.out.println("[" + getBookingType() + "] " + id + " | User: " + userId
                + " | " + resourceId + " | " + eventDate + " " + eventTime
                + " | Guests: " + guests + " | " + status + " | Total: " + calculateTotal());
    }

    /**
     * Helper method to serialize shared attributes cleanly.
     *
     * @return Pipe-delimited base field string
     */
    protected String baseFields() {
        return id + "|" + userId + "|" + resourceId + "|" + eventDate + "|" + eventTime
                + "|" + guests + "|" + status;
    }
}
