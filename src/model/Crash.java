package model;

import java.io.Serializable;

public class Crash implements Serializable {
    public String description;
    public String time;
    public String positionOfCrash;

    public Crash(String d,String t,String p){
        description=d;
        time=t;
        positionOfCrash=p;
    }
    public String getDescription(){
        return description;
    }
    public String getTime(){
        return time;
    }
    public String getPositionOfCrash(){
        return positionOfCrash;
    }

    @Override
    public String toString() {
        return description+ " "+time+ " "+ positionOfCrash;
    }
}