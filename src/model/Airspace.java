package model;


import applications.Simulator;

import java.util.ArrayList;
import java.util.Iterator;

public class Airspace extends Thread {

    public static Field[][] fields;
    private int numberOfAircrafts = 0;
    private int skyX;
    private int skyY;
    public static boolean noFly = false;
    private static int enemiesInSky = 0;
    private static int inLandInSky = 0;
    public static boolean isEnemyInSky = false;
    private ArrayList<Integer> idsOfAircraftInAccidents;//idsOfAircraftInAccidents
    private ArrayList<Integer> idsOfAircraftToEleminate;
    private int numberOfAircraftInAccidents;
    private ArrayList<Integer> idsOfEnemisAircraft;//da simulator zna koga da prati

    public int getNumberOfEnemisAircraft() {
        return idsOfEnemisAircraft.size();
    }

    public void addIdsOfEnemisAircraft(int i) {
        idsOfEnemisAircraft.add(i);
    }

    public int getIdsOfEnemisAircraft() {
        if (idsOfEnemisAircraft.isEmpty()) return 0;
        int i = idsOfEnemisAircraft.get(0);//uvijek ce biti samo jedan neprijateljksi
        Iterator itr = idsOfEnemisAircraft.iterator();
        while (itr.hasNext()) {
            int x = (Integer) itr.next();
            if (x == i)
                itr.remove();
        }
        return i;
    }

    public Integer[] getIdsOfAircraftToEleminate() {
        Integer[] arr = new Integer[idsOfAircraftToEleminate.size()];
        arr = idsOfAircraftToEleminate.toArray(arr);
        return arr;
    }

    public int getNumberOfAircraftInAccidents() {
        return numberOfAircraftInAccidents;
    }

    public Airspace() {
        idsOfAircraftToEleminate = new ArrayList<Integer>();
        idsOfAircraftInAccidents = new ArrayList<Integer>();
        idsOfEnemisAircraft = new ArrayList<Integer>();
        numberOfAircraftInAccidents = 0;
    }

    public synchronized void addIdsOfAircraftInAccidents(int i) {
        idsOfAircraftInAccidents.add(i);
        idsOfAircraftToEleminate.add(i);
        numberOfAircraftInAccidents++;

    }

    public void remuveIdsOfAircraftInAccidents(int i) {
        Iterator itr = idsOfAircraftInAccidents.iterator();
        while (itr.hasNext()) {
            int x = (Integer) itr.next();
            if (x == i)
                itr.remove();
        }

    }

    public void remuveIdsOfAircraftToEleminate(int i) {
        Iterator itr = idsOfAircraftToEleminate.iterator();
        while (itr.hasNext()) {
            int x = (Integer) itr.next();
            if (x == i)
                itr.remove();
        }
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
        if (x >= 0 && x < skyX && y >= 0 && y < skyY) {
            fields[x][y].setAircraftMark(mark);
            fields[x][y].setId(id);
            numberOfAircrafts++;
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
        if(id<600) {
            Aircraft a = (Aircraft) Simulator.aircrafts.get(id);
            if ( a.isEnemy() && check(xPosition, yPosition, flightIndex, mark, id, height)) return -1;
        }else{
            Rocket a=(Rocket)Simulator.rockets.get(id);
            if ( a.isEnemy() && check(xPosition, yPosition, flightIndex, mark, id, height)) return -1;

        }

        if (idsOfAircraftInAccidents.contains(id)) return -1;



        int f = flightIndex;
        if (flightIndex == 0) {
            //System.out.println(flightIndex);
            if (yPosition == 0) {
                f = -1;
                numberOfAircrafts--;

            } else {
                if (!fields[xPosition][yPosition - 1].getAircraftMark().equals("   ")) {
                    if (yPosition != skyY && crash(xPosition, yPosition, flightIndex, mark, id, height)) return -1;
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
                    if (xPosition != skyX && crash(xPosition, yPosition, flightIndex, mark, id, height)) return -1;
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
                if (yPosition != -1 && !fields[xPosition][yPosition + 1].getAircraftMark().equals("   ")) {
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
                if (xPosition != -1 && !fields[xPosition + 1][yPosition].getAircraftMark().equals("   ")) {
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
        if (yPosition != skyY && xPosition != skyX && yPosition != -1 && xPosition != -1 && fields[xPosition][yPosition].isSecondAircraft()) {//za dve letjelice na istom polju


            if (fields[xPosition][yPosition].getAircraftMark().equals(mark)) {
                fields[xPosition][yPosition].setAircraftMark(fields[xPosition][yPosition].getSecondMar());
                fields[xPosition][yPosition].setId(fields[xPosition][yPosition].getSecondId());
                fields[xPosition][yPosition].setHeightOfTheFlight(fields[xPosition][yPosition].getSecondHeightOfTheFlight());
            }


            fields[xPosition][yPosition].setSecondAircraft(false);
            fields[xPosition][yPosition].setSecondId(0);
            fields[xPosition][yPosition].setSecondMar("   ");
            fields[xPosition][yPosition].setSecondHeightOfTheFlight(0.0);


        } else if (yPosition != skyY && xPosition != skyX && yPosition != -1 && xPosition != -1) {
            fields[xPosition][yPosition].setId(0);
            fields[xPosition][yPosition].setAircraftMark("   ");
            fields[xPosition][yPosition].setHeightOfTheFlight(0.0);
        }
        //System.out.println("u letu pozocije poslije pomicaja i ideks"+ xPosition+" "+yPosition+" "+flightIndex);
        return f;
    }

    public synchronized boolean check(int xPosition, int yPosition, int flightIndex, String mark, int id, double heightOfTheFlight) {
        Aircraft a = null;
        if (flightIndex == 0) {
            if (xPosition == 0 && yPosition != skyY - 1) //gornje  polje,uzima iza sebe
                a = (Aircraft) Simulator.aircrafts.get(fields[xPosition][yPosition + 1].getId());//uzima iza sebe
            else if (xPosition != 0)
                a = (Aircraft) Simulator.aircrafts.get(fields[xPosition - 1][yPosition].getId());//negdje u polju

            if (a != null && id == a.getIdToAttack()) {
                if (fields[xPosition][yPosition].isSecondAircraft()) {//za dve letjelice na istom polju


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
                return true;

            } else {
                if (xPosition == skyX - 1 && yPosition != skyY - 1) //donji desni cosak
                    a = (Aircraft) Simulator.aircrafts.get(fields[xPosition][yPosition + 1].getId());//uzima iza sebe
                else if (xPosition != skyX - 1) {
                    a = (Aircraft) Simulator.aircrafts.get(fields[xPosition + 1][yPosition].getId());
                }
                if (a != null && id == a.getIdToAttack()) {
                    if (fields[xPosition][yPosition].isSecondAircraft()) {//za dve letjelice na istom polju


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
                    return true;

                }

            }

        }
        if (flightIndex == 1) {
            if (yPosition == skyY - 1 && xPosition != skyX - 1) //desno  polje,uzima iza sebe
                a = (Aircraft) Simulator.aircrafts.get(fields[xPosition + 1][yPosition].getId());
            else if (yPosition != skyY - 1) {
                a = (Aircraft) Simulator.aircrafts.get(fields[xPosition][yPosition + 1].getId());//negdje u polju
            }
            if (a != null && id == a.getIdToAttack()) {
                if (fields[xPosition][yPosition].isSecondAircraft()) {//za dve letjelice na istom polju


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
                return true;

            } else {
                if (yPosition == 0 && xPosition != skyX - 1) //lijevo  polje,uzima iza sebe
                    a = (Aircraft) Simulator.aircrafts.get(fields[xPosition + 1][yPosition].getId());
                else if (yPosition != 0) {
                    a = (Aircraft) Simulator.aircrafts.get(fields[xPosition][yPosition - 1].getId());//negdje u polju
                }
                if (a != null && id == a.getIdToAttack()) {
                    if (fields[xPosition][yPosition].isSecondAircraft()) {//za dve letjelice na istom polju


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
                    return true;

                }

            }

        }
        if (flightIndex == 2) {
            if (xPosition == skyX - 1 && yPosition != 0) //donje  polje,uzima iza sebe
                a = (Aircraft) Simulator.aircrafts.get(fields[xPosition][yPosition - 1].getId());
            else if (xPosition != skyX - 1) {
                a = (Aircraft) Simulator.aircrafts.get(fields[xPosition + 1][yPosition].getId());//negdje u polju
            }
            if (a != null && id == a.getIdToAttack()) {
                if (fields[xPosition][yPosition].isSecondAircraft()) {//za dve letjelice na istom polju


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
                return true;

            } else {
                if (xPosition == 0 && yPosition != 0) //gornje  polje,uzima iza sebe
                    a = (Aircraft) Simulator.aircrafts.get(fields[xPosition][yPosition - 1].getId());
                else if (xPosition != 0) {
                    a = (Aircraft) Simulator.aircrafts.get(fields[xPosition - 1][yPosition].getId());//negdje u polju
                }
                if (a != null && id == a.getIdToAttack()) {
                    if (fields[xPosition][yPosition].isSecondAircraft()) {//za dve letjelice na istom polju


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
                    return true;

                }

            }

        }
        if (flightIndex == 3) {//stao
            if (yPosition == 0 && xPosition != 0) //lijevo  polje,uzima iza sebe
                a = (Aircraft) Simulator.aircrafts.get(fields[xPosition - 1][yPosition].getId());
            else if (yPosition != 0) {
                a = (Aircraft) Simulator.aircrafts.get(fields[xPosition][yPosition - 1].getId());//negdje u polju
            }
            if (a != null && id == a.getIdToAttack()) {
                if (fields[xPosition][yPosition].isSecondAircraft()) {//za dve letjelice na istom polju


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
                return true;

            } else {
                if (yPosition == skyY-1 && xPosition != 0) //lijevo  polje,uzima iza sebe
                    a = (Aircraft) Simulator.aircrafts.get(fields[xPosition - 1][yPosition].getId());
                else if (yPosition !=skyY-1) {
                    a = (Aircraft) Simulator.aircrafts.get(fields[xPosition][yPosition + 1].getId());//negdje u polju
                }                if (a != null && id == a.getIdToAttack()) {
                    if (fields[xPosition][yPosition].isSecondAircraft()) {//za dve letjelice na istom polju


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
                    return true;

                }

            }

        }

        return false;
    }

    private synchronized boolean crash(int xPosition, int yPosition, int flightIndex, String mark, int id,
                                       double height) {

        if (flightIndex == 0) {
            if (height == fields[xPosition][yPosition - 1].getHeightOfTheFlight()) {
                addIdsOfAircraftInAccidents(id);
                addIdsOfAircraftInAccidents(fields[xPosition][yPosition - 1].getId());
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
            if (height == fields[xPosition - 1][yPosition].getHeightOfTheFlight()) {
                addIdsOfAircraftInAccidents(id);
                addIdsOfAircraftInAccidents(fields[xPosition - 1][yPosition].getId());
                fields[xPosition][yPosition].setId(0);
                fields[xPosition][yPosition].setAircraftMark("   ");
                fields[xPosition][yPosition].setHeightOfTheFlight(0.0);
                fields[xPosition - 1][yPosition].setId(0);
                fields[xPosition - 1][yPosition].setAircraftMark("   ");
                fields[xPosition - 1][yPosition].setHeightOfTheFlight(0.0);
                return true;

            }
            return false;
        } else if (flightIndex == 2) {

            if (height == fields[xPosition][yPosition + 1].getHeightOfTheFlight()) {
                addIdsOfAircraftInAccidents(id);
                addIdsOfAircraftInAccidents(fields[xPosition][yPosition + 1].getId());
                fields[xPosition][yPosition].setId(0);
                fields[xPosition][yPosition].setAircraftMark("   ");
                fields[xPosition][yPosition].setHeightOfTheFlight(0.0);
                fields[xPosition][yPosition + 1].setId(0);
                fields[xPosition][yPosition + 1].setAircraftMark("   ");
                fields[xPosition][yPosition + 1].setHeightOfTheFlight(0.0);
                return true;
            }
            return false;
        } else {


            if (height == fields[xPosition + 1][yPosition].getHeightOfTheFlight()) {
                addIdsOfAircraftInAccidents(id);
                addIdsOfAircraftInAccidents(fields[xPosition - 1][yPosition].getId());
                fields[xPosition][yPosition].setId(0);
                fields[xPosition][yPosition].setAircraftMark("   ");
                fields[xPosition][yPosition].setHeightOfTheFlight(0.0);
                fields[xPosition + 1][yPosition].setId(0);
                fields[xPosition + 1][yPosition].setAircraftMark("   ");
                fields[xPosition + 1][yPosition].setHeightOfTheFlight(0.0);
                return true;
            }
            return false;
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

    public boolean isInList(int i) {
        return idsOfAircraftInAccidents.contains(i);
    }


}
