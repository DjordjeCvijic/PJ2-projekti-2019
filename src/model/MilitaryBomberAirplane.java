package model;

import java.util.List;
import java.util.Map;

public class MilitaryBomberAirplane extends Aircraft{
    private boolean inland;
    public MilitaryBomberAirplane(String model, double height, Map characteristics, List persons, boolean inland) {
        super(model, height, characteristics, persons, "MBA");
        this.inland=inland;
    }

    public boolean isInland(){
        return inland;
    }
}
