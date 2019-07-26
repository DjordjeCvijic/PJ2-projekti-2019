package model;

import java.util.List;
import java.util.Map;

public class FireHelicopter extends Aircraft {
    private double waterCapacity;

    public FireHelicopter(String model, double height, Map characteristics, List persons, double water){
        super(model,height,characteristics,persons,"FH");
        waterCapacity=water;
    }

}
