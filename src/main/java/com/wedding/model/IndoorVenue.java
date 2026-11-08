package com.wedding.model;

public class IndoorVenue extends Venue {
    private boolean airConditioned;

    public IndoorVenue(String id, String name, String location, int capacity,
                       double pricePerHour, boolean airConditioned) {
        super(id, name, location, capacity, pricePerHour);
        this.airConditioned = airConditioned;
    }

    public boolean isAirConditioned() { return airConditioned; }
    public void setAirConditioned(boolean airConditioned) { this.airConditioned = airConditioned; }

    @Override
    public String getType() { return "INDOOR"; }

    @Override
    public double calculateCost(int hours) {
        double cost = getPricePerHour() * hours;
        if (airConditioned) {
            cost += 2000 * hours;        // AC charge per hour
        }
        return cost;
    }

    @Override
    public String toFileString() { return baseFields() + "|INDOOR|" + airConditioned; }
}
