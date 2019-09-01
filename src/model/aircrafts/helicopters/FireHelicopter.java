package model.aircrafts.helicopters;

import model.Airspace;
import model.aircrafts.Aircraft;
import model.interfaces.FireFightingInterface;

import java.util.List;
import java.util.Map;

public class FireHelicopter extends Aircraft implements FireFightingInterface {
    private double waterCapacity;

    public FireHelicopter(String model, int height, Map characteristics, List persons, double water, Airspace a){
        super(model,height,characteristics,persons,"FrH",a);
        waterCapacity=water;
    }

}
