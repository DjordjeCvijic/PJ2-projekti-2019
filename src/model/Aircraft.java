package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;



public class Aircraft {
    private String model;
    private int id;
    private double heightOfTheFlight;
    private int flightSpeed;
    private Map characteristics;
    private List persons;
    private static int counter=0;

    private String mark;

    public  Aircraft(){};

    public Aircraft(String model,double height,Map characteristics,List persons,String mark){
        this.model=model;
        id=++counter;
        heightOfTheFlight=height;
        this.characteristics=new HashMap<Integer,String>();
        this.characteristics=characteristics;
        this.persons=new ArrayList<Person>();
        this.persons=persons;
        Random ran=new Random();
        flightSpeed=ran.nextInt(3)+1;
        this.mark=mark;


    }

    public String getMark(){
        return mark;
    }
}
