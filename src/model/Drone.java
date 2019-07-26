package model;

import java.util.List;
import java.util.Map;

public class Drone extends Aircraft{

    public Drone(String model, double height, Map characteristics, List persons){
        super(model,height,characteristics,persons,"D");
    }

    public String toString(){
        return("Dron nadgleda");
    }
}
