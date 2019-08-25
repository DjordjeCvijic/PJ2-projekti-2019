package model;

public class MilitaryRocket extends Rocket {
    private boolean inland;

    public MilitaryRocket(double range, double height, boolean i, Airspace a) {

        super(range, height, "MiR", a);
        inland = i;
        if (!inland) {
            System.out.println("u raketi jeste strani");
            setEnemy(true);
        }
    }
}
