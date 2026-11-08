package com.wedding.model;

import com.wedding.dao.VendorDao;

public class VendorBooking extends Booking {

    public VendorBooking(String id, String userId, String vendorId, String eventDate,
                         String eventTime, int guests, String status) {
        super(id, userId, vendorId, eventDate, eventTime, guests, status);
    }

    @Override
    public String getBookingType() { return "VENDOR"; }

    @Override
    public double calculateTotal() {
        Vendor v = new VendorDao().findById(getResourceId());
        if (v == null) return 0;
        return v.calculateCost(getGuests());     // uses the vendor's own pricing
    }

    @Override
    public String toFileString() { return baseFields() + "|VENDOR|0"; }
}
