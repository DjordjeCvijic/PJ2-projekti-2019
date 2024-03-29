package model.aircrafts;

import applications.LoggerService;
import applications.Simulator;
import model.Airspace;
import model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;


public class Aircraft extends Thread {
    private String model;
    private int id;
    private int heightOfTheFlight;
    private int flightSpeed;
    private Map characteristics;
    private List persons;
    private static int counter = 0;
    private int flightIndex;
    private int xPosition;
    private int yPosition;
    private boolean canFly = true;
    private boolean enemy = false;
    private boolean inAttack = false;
    private int idToAttack = 0;
    private Airspace airspace;
    private String mark;

    public int getFlightIndex() {
        return flightIndex;
    }

    public Aircraft() {
    }

    public int getHeightOfTheFlight() {
        return heightOfTheFlight;
    }

    public Aircraft(String model, int height, Map characteristics, List persons, String mark, Airspace a) {
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


    public String getMark() {
        return mark;
    }

    public int getIdOfAircraft() {
        return id;
    }

    public void setEntrance(int skyX, int skyY) {

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


    }


    public void setEntranceForAttack(int skyX, int skyY, int flightIndex, int xPositionOfEnemy, int yPositionOfEnemy, int num) {
        this.flightIndex = flightIndex;
        if (num == 1) {
            if (flightIndex == 0) {
                if (xPositionOfEnemy == skyX - 1) {//donji desni cosak
                    xPosition = xPositionOfEnemy;
                    yPosition = skyY;
                } else {
                    yPosition = skyY - 1;
                    xPosition = xPositionOfEnemy + 1;
                }
            } else if (flightIndex == 1) {
                if (yPositionOfEnemy == 0) {//donji lijevi cosak
                    yPosition = yPositionOfEnemy;
                    xPosition = skyX;
                } else {
                    xPosition = skyX - 1;
                    yPosition = yPositionOfEnemy - 1;
                }
            } else if (flightIndex == 2) {
                if (xPositionOfEnemy == 0) {//grnji lijevi cosak
                    xPosition = xPositionOfEnemy;
                    yPosition = -1;
                } else {
                    yPosition = 0;
                    xPosition = xPositionOfEnemy - 1;
                }
            } else {
                if (yPositionOfEnemy == skyY - 1) {//gornji desni cosak
                    yPosition = yPositionOfEnemy;
                    xPosition = -1;
                } else {
                    xPosition = 0;
                    yPosition = yPositionOfEnemy + 1;
                }
            }

        } else {//zad drugog napadaca
            if (flightIndex == 0) {
                if (xPositionOfEnemy == 0) {//gornji desni cosak
                    xPosition = xPositionOfEnemy;
                    yPosition = skyY;
                } else {
                    yPosition = skyY - 1;
                    xPosition = xPositionOfEnemy - 1;
                }
            } else if (flightIndex == 1) {
                if (yPositionOfEnemy == skyY - 1) {//donji desni cosak
                    yPosition = skyY - 1;
                    xPosition = skyX;
                } else {
                    xPosition = skyX - 1;
                    yPosition = yPositionOfEnemy + 1;
                }
            } else if (flightIndex == 2) {
                if (xPositionOfEnemy == skyX - 1) {//donji lijevi cosak
                    xPosition = skyX - 1;
                    yPosition = -1;
                } else {
                    yPosition = 0;
                    xPosition = xPositionOfEnemy + 1;
                }
            } else {
                if (yPositionOfEnemy == 0) {//gornji lijevi cosak
                    yPosition = 0;
                    xPosition = -1;
                } else {
                    xPosition = 0;
                    yPosition = yPositionOfEnemy - 1;
                }
            }


        }


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

            if (canFly) {
                try {
                    sleep(flightSpeed * 1000);

                } catch (Exception e) {
                    LoggerService logger = LoggerService.getInstance();
                    logger.log(Level.WARNING, e);
                }
                c = airspace.flight(xPosition, yPosition, flightIndex, mark, id, heightOfTheFlight);

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
                    sleep(flightSpeed * 1000);

                } catch (Exception e) {
                    LoggerService logger = LoggerService.getInstance();
                    logger.log(Level.WARNING, e);
                }
                flightIndex = modFlightIndex(flightIndex);//indesk za skretanje
                c = airspace.flight(xPosition, yPosition, flightIndex, mark, id, heightOfTheFlight);
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

        } while (c != -1);
        airspace.removeIdsOfAircraftInAccidents(id);
        if (enemy) {
            Airspace.decrementEnemiesInSky();
            Simulator.noFlightZoneDeactivate();
        }


    }


    private int modFlightIndex(int index) {
        int min;
        int newIndex = index;
        if (index == 0) {
            min = yPosition;
            if (xPosition < min) {
                min = xPosition;
                newIndex = 1;
            }
            if (airspace.getSkyX() - 1 - xPosition < min) {
                newIndex = 3;
            }


        } else if (index == 1) {
            min = xPosition;
            if (yPosition < min) {
                min = yPosition;
                newIndex = 0;
            }
            if (airspace.getSkyY() - 1 - yPosition < min) {
                newIndex = 2;
            }

        } else if (index == 2) {
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

    public int getIdToAttack() {
        return idToAttack;
    }

    public void setIdToAttack(int idToAttack) {
        this.idToAttack = idToAttack;
    }

    public boolean isInAttack() {
        return inAttack;
    }

    public void setInAttack(boolean inAttack) {
        this.inAttack = inAttack;
    }

    public void setFlightSpeed(int flightSpeed) {
        this.flightSpeed = flightSpeed;
    }
}
