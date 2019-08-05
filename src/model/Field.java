package model;

import java.util.ArrayList;

public class Field {
    private String aircraftMark;
    private int id;
    private Double heightOfTheFlight;

    private boolean secondAircraft = false;
    private int secondId;
    private String secondMar;
    private Double secondHeightOfTheFlight;

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

    public Double getSecondHeightOfTheFlight() {
        return secondHeightOfTheFlight;
    }

    public void setSecondHeightOfTheFlight(Double secondHeightOfTheFlight) {
        this.secondHeightOfTheFlight = secondHeightOfTheFlight;
    }

    public Double getHeightOfTheFlight() {
        return heightOfTheFlight;
    }

    public void setHeightOfTheFlight(Double heightOfTheFlight) {
        this.heightOfTheFlight = heightOfTheFlight;
    }


}
