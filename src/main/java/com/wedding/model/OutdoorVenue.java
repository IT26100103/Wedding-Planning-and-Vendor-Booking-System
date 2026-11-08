package com.wedding.model;

public class OutdoorVenue extends Venue {
    private boolean tentProvided;

    public OutdoorVenue(String id, String name, String location, int capacity,
                        double pricePerHour, boolean tentProvided) {
        super(id, name, location, capacity, pricePerHour);
        this.tentProvided = tentProvided;
    }

    public boolean isTentProvided() { return tentProvided; }
    public void setTentProvided(boolean tentProvided) { this.tentProvided = tentProvided; }

    @Override
    public String getType() { return "OUTDOOR"; }

    @Override
    public double calculateCost(int hours) {
        double cost = getPricePerHour() * hours;
        if (tentProvided) {
            cost += 25000;               // one-time tent charge
        }
        return cost;
    }

    @Override
    public String toFileString() { return baseFields() + "|OUTDOOR|" + tentProvided; }
}
