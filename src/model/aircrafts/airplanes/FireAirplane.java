package model.aircrafts.airplanes;

import model.Airspace;
import model.aircrafts.Aircraft;
import model.interfaces.FireFightingInterface;

import java.util.List;
import java.util.Map;

public class FireAirplane extends Aircraft implements FireFightingInterface {
    private double waterCapacity;

    public FireAirplane(String model, int height, Map characteristics, List persons, double water, Airspace a) {
        super(model, height, characteristics, persons, "FrA", a);
        waterCapacity = water;
    }


}
