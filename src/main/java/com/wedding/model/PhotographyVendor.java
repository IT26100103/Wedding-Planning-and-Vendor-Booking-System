package com.wedding.model;

/**
 * ============================================================================
 * CONCRETE CLASS: PhotographyVendor
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. INHERITANCE:
 *    - Extends abstract class Vendor, inheriting id, name, phone, and basePrice.
 * 2. POLYMORPHISM:
 *    - Overrides calculateCost(guests): for photography, service fees are package-based
 *      and remain constant regardless of guest count (basePrice).
 *    - Overrides getCategory() to return "PHOTOGRAPHY".
 *    - Overrides toFileString() to append category tag.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class PhotographyVendor extends Vendor {

    /**
     * Parameterized constructor for creating a photography studio vendor.
     *
     * @param id           Unique vendor ID
     * @param name         Photography business name
     * @param phone        Contact number
     * @param packagePrice Fixed coverage package rate
     */
    public PhotographyVendor(String id, String name, String phone, double packagePrice) {
        super(id, name, phone, packagePrice);
    }

    /**
     * Identifies vendor category.
     *
     * @return "PHOTOGRAPHY"
     */
    @Override
    public String getCategory() { return "PHOTOGRAPHY"; }

    /**
     * Constant pricing package: independent of guest count.
     *
     * @param guests Number of guests (ignored for package rate)
     * @return Fixed photography package price
     */
    @Override
    public double calculateCost(int guests) { return getBasePrice(); }

    /**
     * Serializes record to pipe-delimited persistence line:
     * format: id|name|phone|basePrice|PHOTOGRAPHY
     *
     * @return Serialized line for vendors.txt
     */
    @Override
    public String toFileString() { return baseFields() + "|PHOTOGRAPHY"; }
}
