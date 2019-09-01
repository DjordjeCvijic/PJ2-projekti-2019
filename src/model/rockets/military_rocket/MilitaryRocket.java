package model.rockets.military_rocket;

import model.Airspace;
import model.rockets.Rocket;

public class MilitaryRocket extends Rocket {
    private boolean inland;

    public MilitaryRocket(double range, int height, boolean i, Airspace a) {

        super(range, height, "MiR", a);
        inland = i;
        if (!inland) {
            System.out.println("u raketi jeste strani");
            setEnemy(true);
        }
    }
}
