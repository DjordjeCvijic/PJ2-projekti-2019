package model;

import java.util.List;
import java.util.Map;

public class TransportHelicopter  extends Aircraft{
    private String cargo;
    private double maximumWeightOfCargo;

    public TransportHelicopter(String model, double height, Map characteristics, List persons, String cargo,double max,Airspace a){
        super(model,height,characteristics,persons,"TrH",a);
        this.cargo=cargo;
        maximumWeightOfCargo=max;
    }
}
