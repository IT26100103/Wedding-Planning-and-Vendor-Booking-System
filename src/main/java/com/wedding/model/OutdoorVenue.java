package com.wedding.model;

/**
 * ============================================================================
 * CONCRETE CLASS: OutdoorVenue
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. INHERITANCE:
 *    - Extends Venue base class, inheriting id, name, location, capacity, and pricePerHour.
 * 2. SPECIALIZATION:
 *    - Adds tentProvided flag indicating if marquee / canopy tent setup is included.
 * 3. POLYMORPHISM:
 *    - getType(): returns "OUTDOOR".
 *    - calculateCost(hours): calculates base hourly rate plus one-time tent fee (LKR 25,000) if selected.
 *    - toFileString(): appends "|OUTDOOR|tentProvided" to file record.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class OutdoorVenue extends Venue {
    /** Whether an outdoor weather canopy / tent is provided by the venue */
    private boolean tentProvided;

    /**
     * Parameterized constructor for open-air lawn, garden, or beach venue.
     *
     * @param id           Venue identifier
     * @param name         Venue name
     * @param location     Location address / city
     * @param capacity     Maximum capacity of guests
     * @param pricePerHour Base rental rate per hour
     * @param tentProvided True if marquee tent package is included
     */
    public OutdoorVenue(String id, String name, String location, int capacity,
                        double pricePerHour, boolean tentProvided) {
        super(id, name, location, capacity, pricePerHour);
        this.tentProvided = tentProvided;
    }

    public boolean isTentProvided() { return tentProvided; }
    public void setTentProvided(boolean tentProvided) { this.tentProvided = tentProvided; }

    @Override
    public String getType() { return "OUTDOOR"; }

    /**
     * Calculates total rental cost:
     * Base hourly price * hours, plus a fixed one-time setup fee of 25,000 if tent canopy is included.
     *
     * @param hours Number of hours reserved
     * @return Total cost
     */
    @Override
    public double calculateCost(int hours) {
        double cost = getPricePerHour() * hours;
        if (tentProvided) {
            cost += 25000;               // one-time tent charge
        }
        return cost;
    }

    @Override
    public String toFileString() { return baseFields() + "|OUTDOOR|" + tentProvided; }
}
