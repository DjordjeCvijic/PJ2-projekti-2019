package model;


import java.io.Serializable;
import java.util.Date;

public class Crash implements Serializable {
    private String description;
    private String time;
    private String positionOfCrash;

    public Crash(String d, String t, String p) {
        description = d;
        time = t;
        positionOfCrash = p;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getPositionOfCrash() {
        return positionOfCrash;
    }

    @Override
    public String toString() {
        return description + " " + new Date(Long.parseLong(time)).toString() + " Position of crash: " + positionOfCrash;
    }
}
