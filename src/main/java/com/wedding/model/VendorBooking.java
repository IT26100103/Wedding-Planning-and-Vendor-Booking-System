package com.wedding.model;

import com.wedding.dao.VendorDao;

/**
 * ============================================================================
 * CONCRETE CLASS: VendorBooking
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. INHERITANCE:
 *    - Extends Booking base class.
 * 2. POLYMORPHISM:
 *    - getBookingType(): returns "VENDOR".
 *    - calculateTotal(): looks up the booked Vendor via VendorDao and invokes
 *      the vendor's polymorphic calculateCost(guests) method.
 *    - toFileString(): appends "|VENDOR|0" for persistence formatting.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class VendorBooking extends Booking {

    /**
     * Parameterized constructor for reserving vendor services.
     *
     * @param id        Booking identifier
     * @param userId    Client user ID
     * @param vendorId  Vendor resource ID
     * @param eventDate Date of event
     * @param eventTime Time of event
     * @param guests    Expected guest count
     * @param status    Booking status (PENDING/CONFIRMED/CANCELLED)
     */
    public VendorBooking(String id, String userId, String vendorId, String eventDate,
                         String eventTime, int guests, String status) {
        super(id, userId, vendorId, eventDate, eventTime, guests, status);
    }

    @Override
    public String getBookingType() { return "VENDOR"; }

    /**
     * Polymorphically computes total cost by delegating to the specific Vendor's
     * pricing calculation formula.
     *
     * @return Total vendor service fee
     */
    @Override
    public double calculateTotal() {
        Vendor v = new VendorDao().findById(getResourceId());
        if (v == null) return 0;
        return v.calculateCost(getGuests());     // uses the vendor's own pricing algorithm
    }

    @Override
    public String toFileString() { return baseFields() + "|VENDOR|0"; }
}
