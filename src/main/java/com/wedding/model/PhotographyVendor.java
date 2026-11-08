package com.wedding.model;

public class PhotographyVendor extends Vendor {

    public PhotographyVendor(String id, String name, String phone, double packagePrice) {
        super(id, name, phone, packagePrice);
    }

    @Override
    public String getCategory() { return "PHOTOGRAPHY"; }

    @Override
    public double calculateCost(int guests) { return getBasePrice(); }   // same price for any guest count

    @Override
    public String toFileString() { return baseFields() + "|PHOTOGRAPHY"; }
}
