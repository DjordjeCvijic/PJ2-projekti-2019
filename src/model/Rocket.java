package model;

import java.util.Random;

public class Rocket extends Thread {

    private double range;
    private double heightOfTheFlight;
    private int flightSpeed;
    private String mark;
    private int id;
    private static int counter1 = 1000;
    private int xPosition;
    private int yPosition;
    private int flightIndex;
    private Airspace airspace;
    private boolean canFliy = true;
    private boolean enemy = false;

    public Rocket() {
    }

    public int getFlightIndex() {
        return flightIndex;
    }

    public Rocket(double range, double height, String mark, Airspace a) {
        this.range = range;
        heightOfTheFlight = height;
        this.mark = mark;
        Random ran = new Random();
        flightSpeed = ran.nextInt(3) + 1;
        id = counter1--;
        airspace = a;
    }

    public String getMark() {
        return mark;
    }

    public int getIdOfRocket() {
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

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void run() {

        int c = 0;
        do {

            if (canFliy) {
                try {
                    sleep(flightSpeed * 1000);

                } catch (Exception e) {
                    e.printStackTrace();
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
                    e.printStackTrace();
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
        if (enemy) {
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
                flightIndex = 0;
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


    public void setCanFliy(boolean canFliy) {
        this.canFliy = canFliy;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }

    public double getHeightOfTheFlight() {
        return heightOfTheFlight;
    }
}
