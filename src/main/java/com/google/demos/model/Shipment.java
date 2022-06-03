package com.google.demos.model;

import java.util.List;

public class Shipment {
    List<ShipmentVisit> deliveries;
    List<ShipmentVisit> pickups;
    String label;










    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ShipmentVisit> getDeliveries() {
        return deliveries;
    }

    public List<ShipmentVisit> getPickups() {
        return pickups;
    }

    public Shipment(List<ShipmentVisit> deliveries, List<ShipmentVisit> pickups) {
        this.deliveries = deliveries;
        this.pickups = pickups;
    }

}
