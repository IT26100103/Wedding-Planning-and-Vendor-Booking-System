package com.wedding.model;

import com.wedding.util.FileUtil;

/**
 * ============================================================================
 * ABSTRACT BASE CLASS: Vendor
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. ABSTRACTION:
 *    - Captures the common attributes (id, name, phone, basePrice) and abstract
 *      contracts (calculateCost, getCategory, toFileString) for all wedding vendors.
 *    - Cannot be instantiated directly; represents the generalized vendor entity.
 * 2. ENCAPSULATION:
 *    - All state variables are private and accessed through public getters and setters.
 * 3. POLYMORPHISM:
 *    - Derived classes provide category-specific pricing algorithms and persistence
 *      formats through overridden methods.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public abstract class Vendor {
    /** Unique alphanumeric vendor identifier (e.g., V001) */
    private String id;

    /** Business / vendor trade name */
    private String name;

    /** Contact telephone number */
    private String phone;

    /** Base service rate or unit price (e.g., per plate for catering, package rate for photography) */
    private double basePrice;

    /**
     * Parameterized constructor for initializing common vendor properties.
     *
     * @param id        Vendor ID
     * @param name      Vendor business name
     * @param phone     Contact telephone number
     * @param basePrice Base service fee or per-unit rate
     */
    public Vendor(String id, String name, String phone, double basePrice) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.basePrice = basePrice;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    /**
     * Polymorphic method: Returns the vendor category classification.
     * Subclasses return specific values such as "CATERING" or "PHOTOGRAPHY".
     *
     * @return vendor category string
     */
    public abstract String getCategory();

    /**
     * Polymorphic method: Computes total estimated cost based on expected guest count.
     * Different vendor types calculate costs differently (e.g. per-plate vs fixed package).
     *
     * @param guests Estimated number of attending guests
     * @return total cost in local currency
     */
    public abstract double calculateCost(int guests);

    /**
     * Polymorphic method: Serializes the vendor instance into a pipe-delimited file format string.
     *
     * @return formatted record for vendors.txt
     */
    public abstract String toFileString();

    /**
     * Console diagnostic display for debugging and CLI reporting.
     *
     * @param guests Guest count for cost calculation
     */
    public void display(int guests) {
        System.out.println("[" + getCategory() + "] " + id + " | " + name + " | " + phone
                + " | Cost for " + guests + " guests: " + calculateCost(guests));
    }

    /**
     * Helper method to serialize shared attributes cleanly.
     *
     * @return pipe-delimited string of base vendor fields
     */
    protected String baseFields() {
        return id + "|" + FileUtil.clean(name) + "|" + FileUtil.clean(phone) + "|" + basePrice;
    }
}
