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
    private int flightIndex;
    private int xPosition;
    private int yPosition;

    private Airspace airspace;

    private String mark;

    public  Aircraft(){};

    public Aircraft(String model,double height,Map characteristics,List persons,String mark,Airspace a){
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
        airspace=a;


    }

    public String getMark(){
        return mark;
    }
    public int getId(){
        return id;
    }
    void setEntrance(int skyX,int skyY){
        Random random=new Random();
        flightIndex=random.nextInt(4);
        if(flightIndex==0){
            yPosition=skyY-1;
            xPosition=random.nextInt(skyX);
        }
        else if(flightIndex==1){
            xPosition=skyX-1;
            yPosition=random.nextInt(skyY);
        }
        else if(flightIndex==2){
            yPosition=0;
            xPosition=random.nextInt(skyX);
        }
        else if(flightIndex==3){
            xPosition=0;
            yPosition=random.nextInt(skyY);
        }
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }
}
