package com.wedding.model;

import com.wedding.util.FileUtil;

/**
 * ============================================================================
 * ABSTRACT BASE CLASS: Vendor
 * ============================================================================
 * OOP CONCEPT: ABSTRACTION & POLYMORPHISM
 * - Base class for third-party service suppliers (caterers, photographers, florists).
 * - Encapsulates: id, name, phone, basePrice.
 * - Declares abstract calculateCost(int guests) and getCategory().
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public abstract class Vendor {
    private String id;
    private String name;
    private String phone;
    private double basePrice;

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

    public abstract String getCategory();
    public abstract double calculateCost(int guests);
    public abstract String toFileString();

    public void display(int guests) {
        System.out.println("[" + getCategory() + "] " + id + " | " + name + " | " + phone
                + " | Cost for " + guests + " guests: " + calculateCost(guests));
    }

    protected String baseFields() {
        return id + "|" + FileUtil.clean(name) + "|" + FileUtil.clean(phone) + "|" + basePrice;
    }
}
