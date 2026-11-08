package com.wedding.model;

public class VipGuest extends Guest {
    private String tableNumber;

    public VipGuest(String guestId, String fullName, String email, String phone,
                    String rsvpStatus, String tableNumber) {
        super(guestId, fullName, email, phone, rsvpStatus);
        this.tableNumber = tableNumber;
    }

    public String getTableNumber() { return tableNumber; }
    public void setTableNumber(String tableNumber) { this.tableNumber = tableNumber; }

    @Override
    public String getGuestType() {
        return "VIP";
    }

    @Override
    public String getSeatingInfo() {
        return "Reserved table " + tableNumber;
    }

    // A VIP line has one extra field, so this child changes the parent version
    @Override
    public String toFileLine() {
        return super.toFileLine() + "|" + tableNumber;
    }
}
