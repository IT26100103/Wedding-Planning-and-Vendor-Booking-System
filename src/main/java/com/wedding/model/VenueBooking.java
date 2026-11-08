package com.wedding.model;

import com.wedding.dao.VenueDao;

/**
 * ============================================================================
 * CONCRETE CLASS: VenueBooking
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. INHERITANCE & SPECIALIZATION:
 *    - Extends Booking base class.
 *    - Adds hours specialization for duration-based venue rental.
 * 2. POLYMORPHISM:
 *    - getBookingType(): returns "VENUE".
 *    - calculateTotal(): looks up the booked Venue via VenueDao and delegates
 *      calculation to the venue's polymorphic calculateCost(hours) method.
 *    - toFileString(): appends "|VENUE|hours" for persistence formatting.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class VenueBooking extends Booking {
    /** Duration in hours for which the wedding venue is reserved */
    private int hours;

    /**
     * Parameterized constructor for wedding venue reservations.
     *
     * @param id        Booking identifier
     * @param userId    Client user ID
     * @param venueId   Venue resource ID
     * @param eventDate Date of event
     * @param eventTime Time of event
     * @param guests    Expected guest count
     * @param status    Booking status (PENDING/CONFIRMED/CANCELLED)
     * @param hours     Duration in hours
     */
    public VenueBooking(String id, String userId, String venueId, String eventDate,
                        String eventTime, int guests, String status, int hours) {
        super(id, userId, venueId, eventDate, eventTime, guests, status);
        this.hours = hours;
    }

    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }

    @Override
    public String getBookingType() { return "VENUE"; }

    /**
     * Polymorphically computes total cost by delegating to the specific Venue's
     * calculateCost(hours) algorithm (including indoor AC or outdoor tent surcharges).
     *
     * @return Total venue rental fee
     */
    @Override
    public double calculateTotal() {
        Venue v = new VenueDao().findById(getResourceId());
        if (v == null) return 0;
        return v.calculateCost(hours);           // uses the venue's own pricing formula
    }

    @Override
    public String toFileString() { return baseFields() + "|VENUE|" + hours; }
}
