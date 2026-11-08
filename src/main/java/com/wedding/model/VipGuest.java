package com.wedding.model;

<<<<<<< HEAD
=======
import com.wedding.util.FileUtil;

/**
 * ============================================================================
 * CONCRETE CLASS: VipGuest
 * ============================================================================
 * OOP CONCEPT: INHERITANCE, SPECIALIZATION & POLYMORPHISM
 * - Represents VIP guests (family, wedding party, special dignitaries).
 * - Specialization: tableNumber (assigned priority seating table).
 * - Implements toFileLine(): VIP|guestId|fullName|email|phone|rsvpStatus|tableNumber
 * - Implements getSeatingInfo(): Table {tableNumber} (Reserved)
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
>>>>>>> origin/main
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
<<<<<<< HEAD
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
=======
    public String getType() { return "VIP"; }

    @Override
    public String getSeatingInfo() {
        if (tableNumber != null && !tableNumber.trim().isEmpty()) {
            return "Table " + tableNumber.trim() + " (Reserved)";
        }
        return "VIP (Reserved)";
    }

    @Override
    public String toFileLine() {
        return "VIP|" + baseFields() + "|" + FileUtil.clean(tableNumber);
>>>>>>> origin/main
    }
}
