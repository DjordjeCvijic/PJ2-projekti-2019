package model.aircrafts.airplanes;

import model.Airspace;
import model.aircrafts.Aircraft;

import java.util.List;
import java.util.Map;

public class TransportAirplane extends Aircraft {

    private double maximumWeightOfCargo;

    public TransportAirplane(String model, int height, Map characteristics, List persons, double max, Airspace a) {
        super(model, height, characteristics, persons, "TrA", a);
        maximumWeightOfCargo = max;
    }

    public double getMaximumWeightOfCargo() {
        return maximumWeightOfCargo;
    }

    public void setMaximumWeightOfCargo(double maximumWeightOfCargo) {
        this.maximumWeightOfCargo = maximumWeightOfCargo;
    }


}
