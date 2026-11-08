package com.wedding.model;

/**
 * ============================================================================
 * CONCRETE CLASS: CateringVendor
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. INHERITANCE:
 *    - Extends abstract class Vendor, inheriting id, name, phone, and basePrice.
 * 2. POLYMORPHISM:
 *    - Overrides calculateCost(guests): for catering, cost scales linearly
 *      as pricePerPlate (basePrice) multiplied by the number of guests.
 *    - Overrides getCategory() to return "CATERING".
 *    - Overrides toFileString() to append category tag.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class CateringVendor extends Vendor {

    /**
     * Parameterized constructor for creating a catering service provider.
     *
     * @param id            Unique vendor ID
     * @param name          Catering service name
     * @param phone         Contact number
     * @param pricePerPlate Price charged per attending guest plate
     */
    public CateringVendor(String id, String name, String phone, double pricePerPlate) {
        super(id, name, phone, pricePerPlate);
    }

    /**
     * Identifies vendor category.
     *
     * @return "CATERING"
     */
    @Override
    public String getCategory() { return "CATERING"; }

    /**
     * Dynamic cost computation based on guest attendance:
     * Cost = price per plate * total guests.
     *
     * @param guests Number of guests
     * @return Total catering expense
     */
    @Override
    public double calculateCost(int guests) { return getBasePrice() * guests; }

    /**
     * Serializes record to pipe-delimited persistence line:
     * format: id|name|phone|basePrice|CATERING
     *
     * @return Serialized line for vendors.txt
     */
    @Override
    public String toFileString() { return baseFields() + "|CATERING"; }
}
