package model.aircrafts.airplanes;

import model.Airspace;
import model.aircrafts.Aircraft;

import java.util.List;
import java.util.Map;

public class PassengerAirplane extends Aircraft {

    private int numberOfSeats;
    private double maximumWeightOfLuggage;


    public PassengerAirplane(String model, int height, Map characteristics, List persons, int numOfSeats, double max, Airspace a) {
        super(model, height, characteristics, persons, "PrA", a);
        numberOfSeats = numOfSeats;
        maximumWeightOfLuggage = max;
    }
}
