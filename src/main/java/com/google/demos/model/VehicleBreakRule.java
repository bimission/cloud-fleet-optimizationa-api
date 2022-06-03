package com.google.demos.model;

import java.util.List;

public class VehicleBreakRule {

    //Sequence of breaks
    List<VehicleBreakRequest> breakRequests;

    //Constraints for breaks
    List<VehicleBreakFrequencyConstraint> frequencyConstraints;

    public void setBreakRequests(List<VehicleBreakRequest> breakRequests) {
        this.breakRequests = breakRequests;
    }

    public void setFrequencyConstraints(List<VehicleBreakFrequencyConstraint> frequencyConstraints) {
        this.frequencyConstraints = frequencyConstraints;
    }
}
