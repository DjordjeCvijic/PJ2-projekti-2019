package model;

import java.util.List;
import java.util.Map;

public class TransportAirplane extends Aircraft{

    private double maximumWeightOfCargo;
    public TransportAirplane(String model, double height, Map characteristics, List persons, double max){
        super(model,height,characteristics,persons,"TA");
        maximumWeightOfCargo=max;
    }
    public double getMaximumWeightOfCargo() {
        return maximumWeightOfCargo;
    }

    public void setMaximumWeightOfCargo(double maximumWeightOfCargo) {
        this.maximumWeightOfCargo = maximumWeightOfCargo;
    }


}
