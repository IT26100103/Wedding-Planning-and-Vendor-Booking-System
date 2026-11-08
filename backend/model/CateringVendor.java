package com.wedding.model;

public class CateringVendor extends Vendor {

    public CateringVendor(String id, String name, String phone, double pricePerPlate) {
        super(id, name, phone, pricePerPlate);
    }

    @Override
    public String getCategory() { 
        return "CATERING"; 
    }

    @Override
    public double calculateCost(int guests) {
        return getBasePrice() * guests; // Per-plate cost calculation
    }

    @Override
    public String toFileString() { 
        return baseFields() + "|CATERING"; 
    }
}
