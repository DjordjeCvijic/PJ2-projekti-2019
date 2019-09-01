package model.aircrafts.helicopters;

import model.Airspace;
import model.aircrafts.Aircraft;

import java.util.List;
import java.util.Map;

public class PassengerHelicopter extends Aircraft {

    private int numberOfSeats;


    public PassengerHelicopter(String model, int height, Map characteristics, List persons, int numOfSeats, Airspace a) {
        super(model, height, characteristics, persons, "PrH", a);
        numberOfSeats = numOfSeats;
    }
}
