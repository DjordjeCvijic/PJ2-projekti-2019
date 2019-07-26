package model;

import java.util.List;
import java.util.Map;

public class TransportHelicopter  extends Aircraft{
    private String cargo;
    private double maximumWeightOfCargo;

    public TransportHelicopter(String model, double height, Map characteristics, List persons, String cargo,double max){
        super(model,height,characteristics,persons,"TH");
        this.cargo=cargo;
        maximumWeightOfCargo=max;
    }
}
