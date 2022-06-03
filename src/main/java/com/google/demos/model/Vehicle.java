package com.google.demos.model;

import java.util.Map;

public class Vehicle {

    Location startLocation;
    Location endLocation;
    Map<String,VehicleLoadLimit> loadLimits;
    String label;
    VehicleBreakRule breakRule;

    double costPerHour; // applied to total time taken by the route (includes travel time, waiting time, visit time)
    double costPerKilometer; // applied to distance
    double fixedCost; //applied when vehicle is used

    VehicleDurationLimit routeDurationLimit;  //full route duration
    VehicleDurationLimit travelDurationLimit; //only traveling included (no waiting, no visit time)

    public void setBreakRule(VehicleBreakRule breakRule) {
        this.breakRule = breakRule;
    }

    public void setRouteDurationLimit(VehicleDurationLimit routeDurationLimit) {
        this.routeDurationLimit = routeDurationLimit;
    }

    public void setTravelDurationLimit(VehicleDurationLimit travelDurationLimit) {
        this.travelDurationLimit = travelDurationLimit;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(double costPerHour) {
        this.costPerHour = costPerHour;
    }

    public double getCostPerKilometer() {
        return costPerKilometer;
    }

    public void setCostPerKilometer(double costPerKilometer) {
        this.costPerKilometer = costPerKilometer;
    }

    public double getFixedCost() {
        return fixedCost;
    }

    public void setFixedCost(double fixedCost) {
        this.fixedCost = fixedCost;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public void setLoadLimits(Map<String, VehicleLoadLimit> loadLimits) {
        this.loadLimits = loadLimits;
    }
}
