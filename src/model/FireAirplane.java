package model;

import java.util.List;
import java.util.Map;

public class FireAirplane extends Aircraft {
    private double waterCapacity;

    public FireAirplane(String model, double height, Map characteristics, List persons, double water, Airspace a) {
        super(model, height, characteristics, persons, "FrA", a);
        waterCapacity = water;
    }


}
