package model;

import java.util.List;
import java.util.Map;

public class Drone extends Aircraft {

    public Drone(String model, double height, Map characteristics, List persons, Airspace a) {
        super(model, height, characteristics, persons, "Drn", a);
    }

    public String toString() {
        return ("Dron nadgleda");
    }
}