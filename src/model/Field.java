package model;

public class Field {
    private String aircraftMark;
    private int id;
    private int[] idsOfAircraftInAccidents;
    private int numberOfAircraftInAccidents;

    public Field(String mark,int id) {
        aircraftMark=mark;
        this.id=id;
    }

    public String getAircraftMark() {
        return aircraftMark;
    }

    public void setAircraftMark(String aircraftMark) {
        this.aircraftMark = aircraftMark;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
