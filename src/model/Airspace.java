package model;



public class Airspace {

   private Field [][] fields;
    private int numberOfAircrafts=0;
    private int skyX;
    private int skyY;
    public Airspace(){}

    public void setAirspace(int i,int j) {
        skyX=i;
        skyY=j;

        fields=new Field[i][j];
        for(int a=0;a<i;a++){
            for(int b=0;b<j;b++){
                fields[a][b]=new Field("   ",0);
            }
        }

    }

    public synchronized void  addObjectOnSky(String mark,int x,int y,int id){//moze se desiti sudar prilikom ulaza.Treba obraditi taj slucaj
        fields[x][y].setAircraftMark(mark);
        fields[x][y].setId(id);
        numberOfAircrafts++;

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

    public synchronized int flight(int xPosition, int yPosition, int flightIndex) {
       int f=flightIndex;
        if(flightIndex==0){
            if(yPosition==0){
                f=-1;
                numberOfAircrafts--;
            }
            else{
                fields[xPosition][yPosition-1].setAircraftMark(fields[xPosition][yPosition].getAircraftMark());
                fields[xPosition][yPosition-1].setId(fields[xPosition][yPosition].getId());

            }
        }else if(flightIndex==1){
            if(xPosition==0){
                f=-1;
                numberOfAircrafts--;
            }
            else{
                fields[xPosition-1][yPosition].setAircraftMark(fields[xPosition][yPosition].getAircraftMark());
                fields[xPosition-1][yPosition].setId(fields[xPosition][yPosition].getId());

            }
        }else if(flightIndex==2){
            if(yPosition==skyY-1){
                f=-1;
                numberOfAircrafts--;
            }
            else{
                fields[xPosition][yPosition+1].setAircraftMark(fields[xPosition][yPosition].getAircraftMark());
                fields[xPosition][yPosition+1].setId(fields[xPosition][yPosition].getId());
            }
        }else{
            if(xPosition==skyX-1){
                f=-1;
                numberOfAircrafts--;
            }
            else{
                fields[xPosition+1][yPosition].setAircraftMark(fields[xPosition][yPosition].getAircraftMark());
                fields[xPosition+1][yPosition].setId(fields[xPosition][yPosition].getId());
            }
        }
        fields[xPosition][yPosition].setId(0);
        fields[xPosition][yPosition].setAircraftMark("   ");
        return f;
    }
}
