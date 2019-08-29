package model;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;


public class MilitaryHunterAirplane extends Aircraft {

    private boolean inland;

    public MilitaryHunterAirplane(String model, int height, Map characteristics, List persons, boolean inland, Airspace a) {
        super(model, height, characteristics, persons, "MHA", a);
        this.inland = inland;
        if (!inland) {
            System.out.println("strana letjelica");
            setEnemy(true);
        }
    }

    public boolean isInland() {
        return inland;
    }
}
