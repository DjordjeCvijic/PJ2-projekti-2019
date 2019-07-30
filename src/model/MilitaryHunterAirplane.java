package model;

import java.util.List;
import java.util.Map;


public class MilitaryHunterAirplane extends Aircraft {

    private boolean inland;
    public MilitaryHunterAirplane(String model, double height, Map characteristics, List persons,boolean inland,Airspace a) {
        super(model, height, characteristics, persons, "MHA",a);
        this.inland=inland;
    }

    public boolean isInland(){
        return inland;
    }
}
