package model.aircrafts.drone;


import model.Airspace;
import model.aircrafts.Aircraft;
import model.interfaces.FieldRecordingInterface;

import java.util.List;
import java.util.Map;

public class Drone extends Aircraft implements FieldRecordingInterface{

    public Drone(String model, int height, Map characteristics, List persons, Airspace a) {
        super(model, height, characteristics, persons, "Drn", a);
    }

    public String toString() {
        return ("Dron nadgleda");
    }
}