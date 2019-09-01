package model.aircrafts.airplanes.military;

import model.Airspace;
import model.aircrafts.Aircraft;
import model.interfaces.AttackingTheTargetInterface;
import model.interfaces.CarryingWeaponsInterface;

import java.util.List;
import java.util.Map;

public class MilitaryBomberAirplane extends Aircraft implements CarryingWeaponsInterface,AttackingTheTargetInterface{
    private boolean inland;

    public MilitaryBomberAirplane(String model, int height, Map characteristics, List persons, boolean inland, Airspace a) {
        super(model, height, characteristics, persons, "MBA", a);
        this.inland = inland;
        if (!inland) {
            System.out.println("strana letjelica");
            setEnemy(true);
        }
    }

    private boolean isInland() {
        return inland;
    }
}
