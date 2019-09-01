package model.aircrafts.helicopters;

import model.Airspace;
import model.aircrafts.Aircraft;

import java.util.List;
import java.util.Map;

public class TransportHelicopter  extends Aircraft {
    private String cargo;
    private double maximumWeightOfCargo;

    public TransportHelicopter(String model, int height, Map characteristics, List persons, String cargo, double max, Airspace a){
        super(model,height,characteristics,persons,"TrH",a);
        this.cargo=cargo;
        maximumWeightOfCargo=max;
    }
}
