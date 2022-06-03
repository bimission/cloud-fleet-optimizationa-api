package com.google.demos.model;

public class VehicleBreakRequest {

    String earliestStartTime;
    String latestStartTime;

    String minDuration;

    public void setEarliestStartTime(String earliestStartTime) {
        this.earliestStartTime = earliestStartTime;
    }

    public void setLatestStartTime(String latestStartTime) {
        this.latestStartTime = latestStartTime;
    }

    public void setMinDuration(String minDuration) {
        this.minDuration = minDuration;
    }
}
