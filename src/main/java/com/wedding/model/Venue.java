package com.wedding.model;

import com.wedding.util.FileUtil;

/**
 * ============================================================================
 * ABSTRACT BASE CLASS: Venue
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. ABSTRACTION:
 *    - Captures common attributes of wedding venues (id, name, location, capacity,
 *      pricePerHour) and abstract methods (getType, calculateCost, toFileString).
 *    - Concrete subclasses represent specialized venue environments (Indoor vs Outdoor).
 * 2. ENCAPSULATION:
 *    - Private attributes with getter and setter accessors.
 * 3. POLYMORPHISM:
 *    - Different subclasses calculate booking rental costs with specific fees
 *      (e.g., AC surcharge for indoor vs tent setup fee for outdoor).
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public abstract class Venue {
    /** Unique venue identifier (e.g., V001) */
    private String id;

    /** Display name of the wedding venue */
    private String name;

    /** Geographic location or address */
    private String location;

    /** Maximum guest capacity */
    private int capacity;

    /** Base hourly rental rate */
    private double pricePerHour;

    /**
     * Parameterized constructor for venue initialization.
     *
     * @param id           Venue identifier
     * @param name         Venue name
     * @param location     Venue physical location
     * @param capacity     Maximum capacity of guests
     * @param pricePerHour Base rental rate per hour
     */
    public Venue(String id, String name, String location, int capacity, double pricePerHour) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.pricePerHour = pricePerHour;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    /**
     * Polymorphic method: Returns the environmental venue type ("INDOOR" or "OUTDOOR").
     *
     * @return Venue type string
     */
    public abstract String getType();

    /**
     * Polymorphic method: Calculates total rental cost based on requested reservation hours.
     * Subclasses apply special amenities pricing (air conditioning or marquee tents).
     *
     * @param hours Number of booked hours
     * @return Calculated total cost
     */
    public abstract double calculateCost(int hours);

    /**
     * Polymorphic method: Serializes the venue instance into a pipe-delimited record.
     *
     * @return Formatted record string for venues.txt
     */
    public abstract String toFileString();

    /**
     * Capacity verification helper to validate whether this venue can accommodate
     * a planned guest list count.
     *
     * @param guests Count of attending guests
     * @return true if capacity is sufficient, false otherwise
     */
    public boolean canHold(int guests) {
        return capacity >= guests;
    }

    /**
     * Diagnostic console display method.
     *
     * @param hours Number of hours
     */
    public void display(int hours) {
        System.out.println("[" + getType() + "] " + id + " | " + name + " | " + location
                + " | Capacity: " + capacity + " | Cost for " + hours + " hrs: " + calculateCost(hours));
    }

    /**
     * Helper method to serialize shared attributes cleanly.
     *
     * @return Pipe-delimited string of base venue fields
     */
    protected String baseFields() {
        return id + "|" + FileUtil.clean(name) + "|" + FileUtil.clean(location) + "|"
                + capacity + "|" + pricePerHour;
    }
}
