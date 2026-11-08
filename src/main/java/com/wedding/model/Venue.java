package com.wedding.model;

import com.wedding.util.FileUtil;

public abstract class Venue {
    private String id;
    private String name;
    private String location;
    private int capacity;
    private double pricePerHour;

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

    // Each child class writes its own version
    public abstract String getType();
    public abstract double calculateCost(int hours);
    public abstract String toFileString();

    // Shared by all venues
    public boolean canHold(int guests) {
        return capacity >= guests;
    }

    public void display(int hours) {
        System.out.println("[" + getType() + "] " + id + " | " + name + " | " + location
                + " | Capacity: " + capacity + " | Cost for " + hours + " hrs: " + calculateCost(hours));
    }

    protected String baseFields() {
        return id + "|" + FileUtil.clean(name) + "|" + FileUtil.clean(location) + "|"
                + capacity + "|" + pricePerHour;
    }
}
