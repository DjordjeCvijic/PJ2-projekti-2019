package model;


import java.util.ArrayList;

public class Airspace extends Thread {

    public static Field[][] fields;
    private int numberOfAircrafts = 0;
    private int skyX;
    private int skyY;
    public static boolean noFly;
    private static int enemiesInSky = 0;
    private static int inLandInSky = 0;
    public static boolean isEnemyInSky = false;
    private ArrayList idsOfAircraftInAccidents;
    private int numberOfAircraftInAccidents;


    public Airspace() {
        idsOfAircraftInAccidents = new ArrayList<Integer>();
        numberOfAircraftInAccidents = 0;
    }

    public void addIdsOfAircraftInAccidents(int i) {
        idsOfAircraftInAccidents.add(i);
        numberOfAircraftInAccidents++;
    }

    public void remuveIdsOfAircraftInAccidents(int i) {
        idsOfAircraftInAccidents.remove(i);
        numberOfAircraftInAccidents--;
    }

    public void setAirspace(int i, int j) {
        skyX = i;
        skyY = j;
        //System.out.println("set airspaca,"+skyX+" "+skyY);//mmmmmmmmmmm

        fields = new Field[i][j];
        for (int a = 0; a < skyX; a++) {
            for (int b = 0; b < skyY; b++) {
                fields[a][b] = new Field("   ", 0);
            }
        }

    }

    public synchronized void addObjectOnSky(String mark, int x, int y, int id) {//moze se desiti sudar prilikom ulaza.Treba obraditi taj slucaj

        fields[x][y].setAircraftMark(mark);
        fields[x][y].setId(id);
        numberOfAircrafts++;

    }


    public void print(int x, int y) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.println(fields[i][j].getAircraftMark() + " " + i + " " + j);
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

        return (fields[i][j].getId() + "#" + fields[i][j].getAircraftMark() + "#" + i + "#" + j);
    }

    public synchronized int flight(int xPosition, int yPosition, int flightIndex, String mark, int id, double height) {
        if(idsOfAircraftInAccidents.contains(id))return -1;

        //System.out.println("u letu pozocije prije pomicaja i ideks"+ xPosition+" "+yPosition+" "+flightIndex);
        int f = flightIndex;
        if (flightIndex == 0) {
            //System.out.println(flightIndex);
            if (yPosition == 0) {
                f = -1;
                numberOfAircrafts--;

            } else {
                if (!fields[xPosition][yPosition - 1].getAircraftMark().equals("   ")) {
                    if (crash(xPosition, yPosition, flightIndex, mark, id, height)) return -1;
                    fields[xPosition][yPosition - 1].setSecondAircraft(true);
                    fields[xPosition][yPosition - 1].setSecondMar(fields[xPosition][yPosition - 1].getAircraftMark());
                    fields[xPosition][yPosition - 1].setSecondId(fields[xPosition][yPosition - 1].getId());
                    fields[xPosition][yPosition - 1].setSecondHeightOfTheFlight(fields[xPosition][yPosition - 1].getHeightOfTheFlight());
                }

                fields[xPosition][yPosition - 1].setAircraftMark(mark);
                fields[xPosition][yPosition - 1].setId(id);
                fields[xPosition][yPosition - 1].setHeightOfTheFlight(height);


            }
        } else if (flightIndex == 1) {
            // System.out.println(flightIndex);
            if (xPosition == 0) {
                f = -1;
                numberOfAircrafts--;
            } else {
                if (!fields[xPosition - 1][yPosition].getAircraftMark().equals("   ")) {
                    if (crash(xPosition, yPosition, flightIndex, mark, id, height)) return -1;
                    fields[xPosition - 1][yPosition].setSecondAircraft(true);
                    fields[xPosition - 1][yPosition].setSecondMar(fields[xPosition - 1][yPosition].getAircraftMark());
                    fields[xPosition - 1][yPosition].setSecondId(fields[xPosition - 1][yPosition].getId());
                    fields[xPosition - 1][yPosition].setSecondHeightOfTheFlight(fields[xPosition - 1][yPosition].getHeightOfTheFlight());
                }
                fields[xPosition - 1][yPosition].setAircraftMark(mark);
                fields[xPosition - 1][yPosition].setId(id);
                fields[xPosition - 1][yPosition].setHeightOfTheFlight(height);

            }
        } else if (flightIndex == 2) {
            //System.out.println(flightIndex);
            if (yPosition == skyY - 1) {
                f = -1;
                numberOfAircrafts--;
            } else {
                if (!fields[xPosition][yPosition + 1].getAircraftMark().equals("   ")) {
                    if (crash(xPosition, yPosition, flightIndex, mark, id, height)) return -1;
                    fields[xPosition][yPosition + 1].setSecondAircraft(true);
                    fields[xPosition][yPosition + 1].setSecondMar(fields[xPosition][yPosition + 1].getAircraftMark());
                    fields[xPosition][yPosition + 1].setSecondId(fields[xPosition][yPosition + 1].getId());
                    fields[xPosition][yPosition + 1].setSecondHeightOfTheFlight(fields[xPosition][yPosition + 1].getHeightOfTheFlight());
                }
                fields[xPosition][yPosition + 1].setAircraftMark(mark);
                fields[xPosition][yPosition + 1].setId(id);
                fields[xPosition][yPosition + 1].setHeightOfTheFlight(height);
            }
        } else {
            //System.out.println(flightIndex);
            if (xPosition == skyX - 1) {
                f = -1;
                numberOfAircrafts--;
            } else {
                if (!fields[xPosition + 1][yPosition].getAircraftMark().equals("   ")) {
                    if (crash(xPosition, yPosition, flightIndex, mark, id, height)) return -1;
                    fields[xPosition + 1][yPosition].setSecondAircraft(true);
                    fields[xPosition + 1][yPosition].setSecondMar(fields[xPosition + 1][yPosition].getAircraftMark());
                    fields[xPosition + 1][yPosition].setSecondId(fields[xPosition + 1][yPosition].getId());
                    fields[xPosition + 1][yPosition].setSecondHeightOfTheFlight(fields[xPosition + 1][yPosition].getHeightOfTheFlight());
                }
                fields[xPosition + 1][yPosition].setAircraftMark(mark);
                fields[xPosition + 1][yPosition].setId(id);
                fields[xPosition + 1][yPosition].setHeightOfTheFlight(height);
            }
        }
        if (fields[xPosition][yPosition].isSecondAircraft()) {


            if (fields[xPosition][yPosition].getAircraftMark().equals(mark)) {
                fields[xPosition][yPosition].setAircraftMark(fields[xPosition][yPosition].getSecondMar());
                fields[xPosition][yPosition].setId(fields[xPosition][yPosition].getSecondId());
                fields[xPosition][yPosition].setHeightOfTheFlight(fields[xPosition][yPosition].getSecondHeightOfTheFlight());
            }


            fields[xPosition][yPosition].setSecondAircraft(false);
            fields[xPosition][yPosition].setSecondId(0);
            fields[xPosition][yPosition].setSecondMar("   ");
            fields[xPosition][yPosition].setSecondHeightOfTheFlight(0.0);


        } else {
            fields[xPosition][yPosition].setId(0);
            fields[xPosition][yPosition].setAircraftMark("   ");
            fields[xPosition][yPosition].setHeightOfTheFlight(0.0);
        }
        //System.out.println("u letu pozocije poslije pomicaja i ideks"+ xPosition+" "+yPosition+" "+flightIndex);
        return f;
    }

    private boolean crash(int xPosition, int yPosition, int flightIndex, String mark, int id, double height) {

        if (flightIndex == 0) {
            if (height == fields[xPosition][yPosition - 1].getHeightOfTheFlight()) {
                idsOfAircraftInAccidents.add(id);
                idsOfAircraftInAccidents.add(fields[xPosition][yPosition - 1].getId());
                fields[xPosition][yPosition].setId(0);
                fields[xPosition][yPosition].setAircraftMark("   ");
                fields[xPosition][yPosition].setHeightOfTheFlight(0.0);
                fields[xPosition][yPosition - 1].setId(0);
                fields[xPosition][yPosition - 1].setAircraftMark("   ");
                fields[xPosition][yPosition - 1].setHeightOfTheFlight(0.0);
                return true;
            }
            return false;
        } else if (flightIndex == 1) {


            ///ovdje stao:radar pregledava idekse sudara i na osnovu njih pravi objekat,radar uklanja idekse iz niza
            //prilikom ulaska u fly letjelica gleda se ima li njegovog ideksa da odmah izadje
        }


    }

    public static boolean isNoFly() {
        return noFly;
    }


    public static void setNoFly(boolean noFly) {
        Airspace.noFly = noFly;
        if (!noFly) {

        }
    }

    public int getEnemiesInSky() {

        return enemiesInSky;
    }

    public static void incramentEnemiesInSky() {

       /*if(enemiesInSky==-1)
           enemiesInSky=1;
       else enemiesInSky++;*/
        enemiesInSky++;
    }

    public static void decramentEnemiesInSky() {
        enemiesInSky--;

    }

    public String getMarkInPosition(int x, int y) {
        return fields[x][y].getAircraftMark();
    }


    public static boolean isIsEnemyInSky() {
        return isEnemyInSky;
    }

    public static void setIsEnemyInSky(boolean isEnemy) {
        Airspace.isEnemyInSky = isEnemy;
    }

    public int getIdInThisPosition(int i, int j) {
        return fields[i][j].getId();
    }

    public static int getIdInThisPositionStatic(int i, int j) {
        return fields[i][j].getId();
    }

}
