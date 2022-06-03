package com.google.demos.model;

import java.util.List;

public class ShipmentVisit {

    Location arrivalLocation;          //
    String duration;                   // in seconds
    List<TimeWindow>  timeWindows;     // okna kiedy ktos bedzie w domu

    String label;

    //if this shipment is not completed - penalty cost is added to the overall cost of the routes

    //double penaltyCost;


    public Location getArrivalLocation() {
        return arrivalLocation;
    }

    public void setArrivalLocation(Location arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<TimeWindow> getTimeWindows() {
        return timeWindows;
    }

    public void setTimeWindows(List<TimeWindow> timeWindows) {
        this.timeWindows = timeWindows;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
