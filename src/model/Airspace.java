package model;



public class Airspace extends Thread{

   private Field [][] fields;
    private int numberOfAircrafts=0;
    private int skyX;
    private int skyY;
    public static boolean noFly;
    private int enemiesInSky=0;
    public Airspace(){}

    public void setAirspace(int i,int j) {
        skyX=i;
        skyY=j;
        //System.out.println("set airspaca,"+skyX+" "+skyY);//mmmmmmmmmmm

        fields=new Field[i][j];
        for(int a=0;a<skyX;a++){
            for(int b=0;b<skyY;b++){
                fields[a][b]=new Field("   ",0);
            }
        }

    }

    public synchronized void  addObjectOnSky(String mark,int x,int y,int id){//moze se desiti sudar prilikom ulaza.Treba obraditi taj slucaj
        /*if(Simulator.isStop()){
            try{
                System.out.println("u addObjectOn sky stop");
                wait();

            }catch (Exception e){
                e.printStackTrace();
            }

        }*/
        fields[x][y].setAircraftMark(mark);
        fields[x][y].setId(id);
        numberOfAircrafts++;

    }

    /*public void run() {
        System.out.println("provjera");

        while(Simulator.isStop()){
            System.out.println("a");

        }
        this.notify();
    }*/
    private  void provjera() {
        System.out.println("provjera");

        while (Simulator.isStop()) {
            System.out.println("a");

        }
        notify();
    }


    public void print(int x,int y){
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                System.out.println(fields[i][j].getAircraftMark()+ " "+ i + " "+j);
            }
        }
    }

    public int getSkyX() {
        return skyX;
    }

    public int getSkyY() {
        return skyY;
    }

    public String getInfo(int i, int j) {

        return(fields[i][j].getId()+"#"+fields[i][j].getAircraftMark()+"#"+i+"#"+j);
    }

    public synchronized int flight(int xPosition, int yPosition, int flightIndex,String mark,int id) {

        //System.out.println("u letu pozocije prije pomicaja i ideks"+ xPosition+" "+yPosition+" "+flightIndex);
       int f=flightIndex;
        if(flightIndex==0){
            //System.out.println(flightIndex);
            if(yPosition==0){
                f=-1;
                numberOfAircrafts--;

            }
            else{
                if(!fields[xPosition][yPosition-1].getAircraftMark().equals("   ")){
                    fields[xPosition][yPosition-1].setSecondAircraft(true);
                    fields[xPosition][yPosition-1].setSecondMar(fields[xPosition][yPosition-1].getAircraftMark());
                    fields[xPosition][yPosition-1].setSecondId(fields[xPosition][yPosition-1].getId());
                }
                fields[xPosition][yPosition-1].setAircraftMark(mark);
                fields[xPosition][yPosition-1].setId(id);

            }
        }else if(flightIndex==1){
           // System.out.println(flightIndex);
            if(xPosition==0){
                f=-1;
                numberOfAircrafts--;
            }
            else{
                if(!fields[xPosition-1][yPosition].getAircraftMark().equals("   ")){
                    fields[xPosition-1][yPosition].setSecondAircraft(true);
                    fields[xPosition-1][yPosition].setSecondMar(fields[xPosition-1][yPosition].getAircraftMark());
                    fields[xPosition-1][yPosition].setSecondId(fields[xPosition-1][yPosition].getId());
                }
                fields[xPosition-1][yPosition].setAircraftMark(mark);
                fields[xPosition-1][yPosition].setId(id);

            }
        }else if(flightIndex==2){
            //System.out.println(flightIndex);
            if(yPosition==skyY-1){
                f=-1;
                numberOfAircrafts--;
            }
            else{
                if(!fields[xPosition][yPosition+1].getAircraftMark().equals("   ")){
                    fields[xPosition][yPosition+1].setSecondAircraft(true);
                    fields[xPosition][yPosition+1].setSecondMar(fields[xPosition][yPosition+1].getAircraftMark());
                    fields[xPosition][yPosition+1].setSecondId(fields[xPosition][yPosition+1].getId());
                }
                fields[xPosition][yPosition+1].setAircraftMark(mark);
                fields[xPosition][yPosition+1].setId(id);
            }
        }else{
            //System.out.println(flightIndex);
            if(xPosition==skyX-1){
                f=-1;
                numberOfAircrafts--;
            }
            else{
                if(!fields[xPosition+1][yPosition].getAircraftMark().equals("   ")){
                    fields[xPosition+1][yPosition].setSecondAircraft(true);
                    fields[xPosition+1][yPosition].setSecondMar(fields[xPosition+1][yPosition].getAircraftMark());
                    fields[xPosition+1][yPosition].setSecondId(fields[xPosition+1][yPosition].getId());
                }
                fields[xPosition+1][yPosition].setAircraftMark(mark);
                fields[xPosition+1][yPosition].setId(id);
            }
        }
        if(fields[xPosition][yPosition].isSecondAircraft()){
            if(fields[xPosition][yPosition].getAircraftMark().equals(mark))
            {
                fields[xPosition][yPosition].setAircraftMark(fields[xPosition][yPosition].getSecondMar());
                fields[xPosition][yPosition].setSecondId(fields[xPosition][yPosition].getSecondId());
                fields[xPosition][yPosition].setSecondAircraft(false);
                fields[xPosition][yPosition].setSecondId(0);
                fields[xPosition][yPosition].setSecondMar("   ");

            }
            else{
                fields[xPosition][yPosition].setSecondAircraft(false);
                fields[xPosition][yPosition].setSecondId(0);
                fields[xPosition][yPosition].setSecondMar("   ");

            }



        }else {
            fields[xPosition][yPosition].setId(0);
            fields[xPosition][yPosition].setAircraftMark("   ");
        }
        //System.out.println("u letu pozocije poslije pomicaja i ideks"+ xPosition+" "+yPosition+" "+flightIndex);
        return f;
    }

    public static boolean isNoFly() {
        return noFly;
    }


    public static void setNoFly(boolean noFly) {
        Airspace.noFly = noFly;
        if(!noFly){

        }
    }

    public int getEnemiesInSky() {
        return enemiesInSky;
    }

    public void setEnemiesInSky(int enemiesInSky) {
        this.enemiesInSky += enemiesInSky;
    }
}
