package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Aircraft extends Thread {
    private String model;
    private int id;
    private double heightOfTheFlight;
    private int flightSpeed;
    private Map characteristics;
    private List persons;
    private static int counter = 0;
    private int flightIndex;
    private int xPosition;
    private int yPosition;
    private boolean canFly = true;
    private boolean enemy=false;

    private Airspace airspace;

    private String mark;

    public Aircraft() {
    }

    public double getHeightOfTheFlight() {
        return heightOfTheFlight;
    }

    public Aircraft(String model, double height, Map characteristics, List persons, String mark, Airspace a) {
        this.model = model;
        id = ++counter;
        heightOfTheFlight = height;
        this.characteristics = new HashMap<Integer, String>();
        this.characteristics = characteristics;
        this.persons = new ArrayList<Person>();
        this.persons = persons;
        Random ran = new Random();
        flightSpeed = ran.nextInt(3) + 1;
        this.mark = mark;
        airspace = a;


    }

    private boolean isInland() {
        return false;
    }

    public String getMark() {
        return mark;
    }

    public int getIdOfAircraft() {
        return id;
    }

    void setEntrance(int skyX, int skyY) {

        // System.out.println("u set ulaz,dimezije mape"+skyX+" "+skyY);//mmmmmmmm
        Random random = new Random();
        flightIndex = random.nextInt(4);
        if (flightIndex == 0) {
            yPosition = skyY - 1;
            xPosition = random.nextInt(skyX);
        } else if (flightIndex == 1) {
            xPosition = skyX - 1;
            yPosition = random.nextInt(skyY);
        } else if (flightIndex == 2) {
            yPosition = 0;
            xPosition = random.nextInt(skyX);
        } else if (flightIndex == 3) {
            xPosition = 0;
            yPosition = random.nextInt(skyY);
        }

        //System.out.println("u set ulaz,i ideks leta:"+xPosition+" "+yPosition +flightIndex);//mmmmmmm
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void run() {


        int c = 0;
        do {
            //if ((mark.equals("MHA") || mark.equals("MBM") || mark.equals("MiR")) || (canFly && airspace.getEnemiesInSky() == 0)) {
            if (canFly) {
                try {
                    sleep(flightSpeed * 1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // System.out.println("u ranu letjelice pozicije"+xPosition+" "+yPosition);

                c = airspace.flight(xPosition, yPosition, flightIndex, mark, id,heightOfTheFlight);
                //System.out.println("u ranu letjelice pozicije i ideks"+xPosition+" "+yPosition+" "+c);
                if (c == 0) {
                    yPosition--;

                } else if (c == 1) {
                    xPosition--;


                } else if (c == 2) {
                    yPosition++;

                } else if (c == 3) {
                    xPosition++;

                }


            } else {
                try {
                    sleep(flightSpeed * 1000);//mozda ne treba

                } catch (Exception e) {
                    e.printStackTrace();
                }
                flightIndex = modFlightIndex(flightIndex);//indesk za skretanje
                // System.out.println(flightIndex);
                c = airspace.flight(xPosition, yPosition, flightIndex, mark, id,heightOfTheFlight);
                if (c == 0) {
                    yPosition--;

                } else if (c == 1) {
                    xPosition--;


                } else if (c == 2) {
                    yPosition++;

                } else if (c == 3) {
                    xPosition++;

                }

            }
            //System.out.println("kraj rana letjelice pozzicije i ideks"+xPosition+" "+yPosition+" "+c);

        } while (c != -1);
        if(enemy){
            Airspace.decramentEnemiesInSky();
        }


    }

    private int modFlightIndex(int indeks) {
        int min;
        int newIndex = indeks;
        if (indeks == 0) {
            min = yPosition;
            if (xPosition < min) {
                min = xPosition;
                newIndex = 1;
            }
            if (airspace.getSkyX() - 1 - xPosition < min) {
                newIndex = 3;
            }


        } else if (indeks == 1) {
            min = xPosition;
            if (yPosition < min) {
                min = yPosition;
                newIndex = 0;
            }
            if (airspace.getSkyY() - 1 - yPosition < min) {
                newIndex = 2;
            }

        } else if (indeks == 2) {
            min = airspace.getSkyY() - 1 - yPosition;
            if (airspace.getSkyX() - 1 - xPosition < min) {
                min = airspace.getSkyX() - 1 - xPosition;
                newIndex = 3;
            }
            if (xPosition < min) {
                newIndex = 1;
            }

        } else {
            min = airspace.getSkyX() - 1 - xPosition;
            if (airspace.getSkyY() - 1 - yPosition < min) {
                min = airspace.getSkyY() - 1 - yPosition;
                newIndex = 2;
            }
            if (yPosition < min) {
                newIndex = 0;
            }
        }


        return newIndex;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }
}
