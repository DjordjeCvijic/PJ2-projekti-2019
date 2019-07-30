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
}
