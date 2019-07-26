package model;

import java.util.List;
import java.util.Map;

public class PessengerAirplane extends Aircraft {

    private int numberOfSeats;
    private double maximumWeightOfLuggage;


    public PessengerAirplane(String model, double height, Map characteristics, List persons, int numOfSeats,double max){
        super(model,height,characteristics,persons,"PA");
        numberOfSeats=numOfSeats;
        maximumWeightOfLuggage=max;
    }
}
