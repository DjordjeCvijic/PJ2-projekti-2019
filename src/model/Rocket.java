package model;
import java.util.Random;

public class Rocket {

    private double range;
    private double heightOfTheFlight;
    private int flightSpeed;
    private String mark;
    private  int id;
    private static int counter1=1000;

    public Rocket(){};

    public Rocket(double range,double height,String mark){
        this.range=range;
        heightOfTheFlight=height;
        this.mark=mark;
        Random ran=new Random();
        flightSpeed=ran.nextInt(3)+1;
        id=counter1--;

    }


}
