package com.wedding.model;

/**
 * ============================================================================
 * CONCRETE CLASS: IndoorVenue
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. INHERITANCE:
 *    - Extends Venue, inheriting common spatial attributes.
 * 2. SPECIALIZATION:
 *    - Adds airConditioned flag to indicate climate control.
 * 3. POLYMORPHISM:
 *    - getType(): returns "INDOOR".
 *    - calculateCost(hours): calculates base hourly rate plus AC hourly surcharge (LKR 2,000/hr).
 *    - toFileString(): appends "|INDOOR|airConditioned" to file persistence.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class IndoorVenue extends Venue {
    /** Whether the indoor hall is equipped with central air conditioning */
    private boolean airConditioned;

    /**
     * Parameterized constructor for an indoor reception hall / ballroom.
     *
     * @param id             Venue ID
     * @param name           Venue name
     * @param location       Venue address / city
     * @param capacity       Maximum seating capacity
     * @param pricePerHour   Base hourly rate
     * @param airConditioned True if air-conditioned
     */
    public IndoorVenue(String id, String name, String location, int capacity,
                       double pricePerHour, boolean airConditioned) {
        super(id, name, location, capacity, pricePerHour);
        this.airConditioned = airConditioned;
    }

    public boolean isAirConditioned() { return airConditioned; }
    public void setAirConditioned(boolean airConditioned) { this.airConditioned = airConditioned; }

    @Override
    public String getType() { return "INDOOR"; }

    /**
     * Calculates total rental cost:
     * Base hourly price * hours, plus additional 2,000/hr if air conditioning is active.
     *
     * @param hours Number of hours reserved
     * @return Total cost
     */
    @Override
    public double calculateCost(int hours) {
        double cost = getPricePerHour() * hours;
        if (airConditioned) {
            cost += 2000 * hours;        // AC charge per hour
        }
        return cost;
    }

    @Override
    public String toFileString() { return baseFields() + "|INDOOR|" + airConditioned; }
}
