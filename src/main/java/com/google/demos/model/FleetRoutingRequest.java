package com.google.demos.model;


//https://cloud.google.com/optimization/docs/reference/rpc/google.cloud.optimization.v1#google.cloud.optimization.v1.Vehicle.LoadLimit
public class FleetRoutingRequest {

    String parent;
    ShipmentModel model;
    String timeout="120s"; //Solver Timeout in seconds
    String searchMode ="RETURN_FAST"; //Alternative: CONSUME_ALL_AVAILABLE_TIME









    boolean populatePolylines = true; //If true, polylines will be populated in response ShipmentRoutes.
    //boolean populateTransitionPolylines = true;



    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public String getParent() {
        return parent;
    }

    public ShipmentModel getModel() {
        return model;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setModel(ShipmentModel model) {
        this.model = model;
    }
}
