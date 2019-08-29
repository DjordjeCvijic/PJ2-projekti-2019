package model;

import java.util.List;
import java.util.Map;

public class FireHelicopter extends Aircraft {
    private double waterCapacity;

    public FireHelicopter(String model, int height, Map characteristics, List persons, double water,Airspace a){
        super(model,height,characteristics,persons,"FrH",a);
        waterCapacity=water;
    }

}
