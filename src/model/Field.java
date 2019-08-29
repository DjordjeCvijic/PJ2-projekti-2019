package model;

import java.util.ArrayList;

public class Field {
    private String aircraftMark;
    private int id;
    private int heightOfTheFlight;

    private boolean secondAircraft = false;
    private int secondId;
    private String secondMar;
    private int secondHeightOfTheFlight;

    public Field(String mark, int id) {
        aircraftMark = mark;
        this.id = id;

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

    public boolean isSecondAircraft() {
        return secondAircraft;
    }

    public void setSecondAircraft(boolean secondAircraft) {
        this.secondAircraft = secondAircraft;
    }

    public String getSecondMar() {
        return secondMar;
    }

    public void setSecondMar(String secondMar) {
        this.secondMar = secondMar;
    }

    public int getSecondId() {
        return secondId;
    }

    public void setSecondId(int secondId) {
        this.secondId = secondId;
    }

    public int getSecondHeightOfTheFlight() {
        return secondHeightOfTheFlight;
    }

    public void setSecondHeightOfTheFlight(int secondHeightOfTheFlight) {
        this.secondHeightOfTheFlight = secondHeightOfTheFlight;
    }

    public synchronized  int getHeightOfTheFlight() {
        return heightOfTheFlight;
    }

    public void setHeightOfTheFlight(int heightOfTheFlight) {
        this.heightOfTheFlight = heightOfTheFlight;
    }


}
