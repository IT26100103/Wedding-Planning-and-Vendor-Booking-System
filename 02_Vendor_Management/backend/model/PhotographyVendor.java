package com.wedding.model;

/**
 * ============================================================================
 * CONCRETE CLASS: PhotographyVendor
 * ============================================================================
 * OOP CONCEPT: INHERITANCE & POLYMORPHISM
 * - Represents wedding photography and videography studios.
 * - calculateCost(guests): flat basePrice package regardless of guest count.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class PhotographyVendor extends Vendor {

    public PhotographyVendor(String id, String name, String phone, double packagePrice) {
        super(id, name, phone, packagePrice);
    }

    @Override
    public String getCategory() { return "PHOTOGRAPHY"; }

    @Override
    public double calculateCost(int guests) {
        return getBasePrice(); // Fixed package price
    }

    @Override
    public String toFileString() { return baseFields() + "|PHOTOGRAPHY"; }
}
