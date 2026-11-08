package com.wedding.model;

import com.wedding.dao.VenueDao;

public class VenueBooking extends Booking {
    private int hours;

    public VenueBooking(String id, String userId, String venueId, String eventDate,
                        String eventTime, int guests, String status, int hours) {
        super(id, userId, venueId, eventDate, eventTime, guests, status);
        this.hours = hours;
    }

    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }

    @Override
    public String getBookingType() { return "VENUE"; }

    @Override
    public double calculateTotal() {
        Venue v = new VenueDao().findById(getResourceId());
        if (v == null) return 0;
        return v.calculateCost(hours);           // uses the venue's own pricing
    }

    @Override
    public String toFileString() { return baseFields() + "|VENUE|" + hours; }
}
