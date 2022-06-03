package com.google.demos.model;

import java.util.Date;
import java.util.List;

public class ShipmentModel {



    List<Shipment> shipments;
    List<Vehicle> vehicles;

    //Powiedzmy ze planujemy kolejny dzien:
    String globalStartTime;
    String globalEndTime;

    //Koszt staly godziny pracy dzialu logistyki
    //globalDurationCostPerHour;

    //Maksymalna ilosc aud, ktore mamy na wyposazeniu
    //maxActiveVehicles;


    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void setGlobalStartTime(String globalStartTime) {
        this.globalStartTime = globalStartTime;
    }

    public void setGlobalEndTime(String globalEndTime) {
        this.globalEndTime = globalEndTime;
    }
}
