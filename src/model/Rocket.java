package model;
import java.util.Random;

public class Rocket extends Thread {

    private double range;
    private double heightOfTheFlight;
    private int flightSpeed;
    private String mark;
    private  int id;
    private static int counter1=1000;
    private int xPosition;
    private int yPosition;
    private int flightIndex;
    private Airspace airspace;

    public Rocket(){};

    public Rocket(double range,double height,String mark,Airspace a){
        this.range=range;
        heightOfTheFlight=height;
        this.mark=mark;
        Random ran=new Random();
        flightSpeed=ran.nextInt(3)+1;
        id=counter1--;
        airspace=a;
    }
    public String getMark(){
        return mark;
    }

    public int getIdOfRocket(){
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
    public void run(){

        while(xPosition<airspace.getSkyX() &&yPosition<airspace.getSkyY()){

            try{
                sleep(flightSpeed*1000);

            }catch (Exception e){
                e.printStackTrace();
            }
            airspace.flight(xPosition,yPosition,flightIndex);





        }
    }



}
