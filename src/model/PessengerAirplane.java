package model;

import java.util.List;
import java.util.Map;

public class PessengerAirplane extends Aircraft {

    private int numberOfSeats;
    private double maximumWeightOfLuggage;


    public PessengerAirplane(String model, int height, Map characteristics, List persons, int numOfSeats, double max, Airspace a) {
        super(model, height, characteristics, persons, "PrA", a);
        numberOfSeats = numOfSeats;
        maximumWeightOfLuggage = max;
    }
}
