package model;

import java.util.List;
import java.util.Map;

public class PessengerHelicopter extends Aircraft {

    private int numberOfSeats;


    public PessengerHelicopter(String model, double height, Map characteristics, List persons, int numOfSeats, Airspace a) {
        super(model, height, characteristics, persons, "PrH", a);
        numberOfSeats = numOfSeats;
    }
}
