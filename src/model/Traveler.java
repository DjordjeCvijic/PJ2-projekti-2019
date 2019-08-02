package model;

public class Traveler extends Person {

    private int numOfPassport;

    public Traveler(String n, String s, int num) {
        super(n, s);
        numOfPassport = num;
    }

    public int getNumOfPassport() {
        return numOfPassport;
    }

    public void setNumOfPassport(int numOfPassport) {
        this.numOfPassport = numOfPassport;
    }
}
