package model;

import java.io.*;
import java.util.*;

public class Simulator extends Thread {
    public int timeInterval;
    private int skyX;
    private int skyY;
    private String[] aircraftToAdd = {"PrA", "PrH", "TrA", "TrH", "FrA", "FrH", "IPR", "Drn", "MHA", "MBA", "MiR"};
    public static Map aircrafts;
    public static Map rockets;
    private final Airspace airspace;
    private long timeStamp;
    private Random random = new Random();
    private static boolean stop = false;
    private int numberOfInlandAircrafts;
    private int numberOfEnemiesAircrafts;


    public Simulator(Airspace a) {
        try {
            BufferedReader in = new BufferedReader(new FileReader("src" + File.separator + "resources" + File.separator + "config.properties.txt"));
            String s = in.readLine();
            s = in.readLine();
            String[] tmp = s.split("#");
            skyX = Integer.parseInt(tmp[0]);
            skyY = Integer.parseInt(tmp[1]);

            // System.out.println("construktor simulatora,dimenzije:"+skyX+" "+skyY);//mmmmmmmmmmmmmmmmmm
            s = in.readLine();
            tmp = s.split("#");
            timeInterval = Integer.parseInt(tmp[0]);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        a.setAirspace(skyX, skyY);
        airspace = a;
        aircrafts = new HashMap<Integer, Aircraft>();
        rockets = new HashMap<Integer, Rocket>();


    }


    public void run() {
        File f = new File("src" + File.separator + "resources" + File.separator + "config.properties.txt");
        Aircraft newAircraft = null;
        Rocket newRocket = null;


        this.timeStamp = f.lastModified();
        while (true) {
            synchronized (airspace) {

                if (stop) {
                    try {
                        System.out.println("wait u simulatoru");
                        airspace.wait();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Integer idOfEnemy = airspace.getIdsOfEnemisAircraft();
                if (idOfEnemy != 0) {
                    System.out.println(idOfEnemy);
                    sendMilitaryAircraft(idOfEnemy);

                }


                /*//samo za testiranje sudara:
                newAircraft = new PessengerAirplane(Integer.toString(random.nextInt(1000)), 100, new HashMap<Integer, Object>(), new ArrayList(),
                        random.nextInt(200) + 1, random.nextDouble() * 100, airspace);
                newAircraft.setEntrance1(skyX, skyY);

                aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                newAircraft.start();

                Aircraft newAircraft1 = new PessengerAirplane(Integer.toString(random.nextInt(1000)), 100, new HashMap<Integer, Object>(), new ArrayList(),
                        random.nextInt(200) + 1, random.nextDouble() * 100, airspace);
                newAircraft1.setEntrance2(skyX, skyY);

                aircrafts.put(newAircraft1.getIdOfAircraft(), newAircraft1);
                airspace.addObjectOnSky(newAircraft1.getMark(), newAircraft1.getXPosition(), newAircraft1.getYPosition(), newAircraft1.getIdOfAircraft());
                newAircraft1.start();*/


                else if (isFileUpdated(f) || numberOfEnemiesAircrafts > 0 || numberOfInlandAircrafts > 0) {
                    try {
                        BufferedReader in = new BufferedReader(new FileReader(f));
                        String s = in.readLine();
                        String[] tmp = s.split("#");
                        numberOfEnemiesAircrafts = Integer.parseInt(tmp[0]);
                        numberOfInlandAircrafts = Integer.parseInt(tmp[1]);
                        int tmp1 = numberOfEnemiesAircrafts;
                        //int tmp2=numberOfInlandAircrafts;

                        if (numberOfInlandAircrafts > 0) {
                            String nextAircraft = aircraftToAdd[random.nextInt(3) + 8];

                            addMilitaryAircraft(nextAircraft, true);
                            numberOfInlandAircrafts--;


                        } else if (numberOfEnemiesAircrafts > 0) {
                            String nextAircraft = aircraftToAdd[random.nextInt(3) + 8];
                            addMilitaryAircraft(nextAircraft, false);
                            numberOfEnemiesAircrafts--;


                        }
                        in.close();
                        BufferedWriter out = new BufferedWriter(new PrintWriter(f));
                        out.write(numberOfEnemiesAircrafts + "#" + numberOfInlandAircrafts + "\r\n" + skyX + "#" + skyY + "\r\n" + Integer.toString(timeInterval));

                        out.close();
                        timeStamp = f.lastModified();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {


                    String nextAircraft = aircraftToAdd[random.nextInt(8)];
                    if ("PrA".equals(nextAircraft)) {
                        newAircraft = new PessengerAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextInt(200) + 1, random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("PrH".equals(nextAircraft)) {
                        newAircraft = new PessengerHelicopter(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextInt(200) + 1, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("TrA".equals(nextAircraft)) {
                        newAircraft = new TransportAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("TrH".equals(nextAircraft)) {
                        newAircraft = new TransportHelicopter(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                                "Prtljaga", random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("FrA".equals(nextAircraft)) {
                        newAircraft = new FireAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("FrH".equals(nextAircraft)) {
                        newAircraft = new FireHelicopter(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("IPR".equals(nextAircraft)) {
                        newRocket = new IceProtectionRocket(random.nextDouble() * 100, random.nextDouble() * 100, airspace);
                        newRocket.setEntrance(skyX, skyY);

                        rockets.put(newRocket.getIdOfRocket(), newRocket);
                        airspace.addObjectOnSky(newRocket.getMark(), newRocket.getXPosition(), newRocket.getYPosition(), newRocket.getIdOfRocket());
                        newRocket.start();

                    } else if ("Drn".equals(nextAircraft)) {
                        newAircraft = new Drone(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                                airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();
                    }


                }
                try {
                    System.out.println();
                    airspace.wait(timeInterval * 1000);
                    //sleep(timeInterval * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            //airspace.print(skyX,skyY);

        }


    }

    private synchronized void sendMilitaryAircraft(Integer idOfEnemy) {
        if (idOfEnemy < 600) {
            Aircraft objectToAttack = (Aircraft) aircrafts.get(idOfEnemy);

            Aircraft newAircraftForAttack = new MilitaryHunterAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                    true, airspace);
            newAircraftForAttack.setEntranceForAttack(skyX, skyY, objectToAttack.getFlightIndex(), objectToAttack.getXPosition(), objectToAttack.getYPosition(), 1);
            newAircraftForAttack.setInAttack(true);
            newAircraftForAttack.setIdToAttack(idOfEnemy);
            aircrafts.put(newAircraftForAttack.getIdOfAircraft(), newAircraftForAttack);
            airspace.addObjectOnSky(newAircraftForAttack.getMark(), newAircraftForAttack.getXPosition(), newAircraftForAttack.getYPosition(), newAircraftForAttack.getIdOfAircraft());
            newAircraftForAttack.start();


            Aircraft newAircraftForAttack1 = new MilitaryBomberAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                    true, airspace);
            newAircraftForAttack1.setEntranceForAttack(skyX, skyY, objectToAttack.getFlightIndex(), objectToAttack.getXPosition(), objectToAttack.getYPosition(), 2);
            newAircraftForAttack1.setInAttack(true);
            newAircraftForAttack1.setIdToAttack(idOfEnemy);
            aircrafts.put(newAircraftForAttack1.getIdOfAircraft(), newAircraftForAttack1);
            airspace.addObjectOnSky(newAircraftForAttack1.getMark(), newAircraftForAttack1.getXPosition(), newAircraftForAttack1.getYPosition(), newAircraftForAttack1.getIdOfAircraft());
            newAircraftForAttack1.start();
        } else {
            Rocket objectToAttack = (Rocket) rockets.get(idOfEnemy);

            Aircraft newAircraftForAttack = new MilitaryHunterAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                    true, airspace);
            newAircraftForAttack.setEntranceForAttack(skyX, skyY, objectToAttack.getFlightIndex(), objectToAttack.getXPosition(), objectToAttack.getYPosition(), 1);
            newAircraftForAttack.setInAttack(true);
            newAircraftForAttack.setIdToAttack(idOfEnemy);
            aircrafts.put(newAircraftForAttack.getIdOfAircraft(), newAircraftForAttack);
            airspace.addObjectOnSky(newAircraftForAttack.getMark(), newAircraftForAttack.getXPosition(), newAircraftForAttack.getYPosition(), newAircraftForAttack.getIdOfAircraft());
            newAircraftForAttack.start();


            Aircraft newAircraftForAttack1 = new MilitaryBomberAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                    true, airspace);
            newAircraftForAttack1.setEntranceForAttack(skyX, skyY, objectToAttack.getFlightIndex(), objectToAttack.getXPosition(), objectToAttack.getYPosition(), 2);
            newAircraftForAttack1.setInAttack(true);
            newAircraftForAttack1.setIdToAttack(idOfEnemy);
            aircrafts.put(newAircraftForAttack1.getIdOfAircraft(), newAircraftForAttack1);
            airspace.addObjectOnSky(newAircraftForAttack1.getMark(), newAircraftForAttack1.getXPosition(), newAircraftForAttack1.getYPosition(), newAircraftForAttack1.getIdOfAircraft());
            newAircraftForAttack1.start();


        }


    }


    private void addMilitaryAircraft(String mark, boolean inland) {
        if ("MHA".equals(mark)) {
            Aircraft newAircraft = new MilitaryHunterAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                    inland, airspace);
            newAircraft.setEntrance(skyX, skyY);
            aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
            airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
            newAircraft.start();
        } else if ("MBA".equals(mark)) {
            Aircraft newAircraft = new MilitaryBomberAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                    inland, airspace);
            newAircraft.setEntrance(skyX, skyY);
            aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
            airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
            newAircraft.start();

        } else {
            Rocket newRocket = new MilitaryRocket(random.nextDouble() * 100, random.nextDouble() * 100, inland, airspace);
            newRocket.setEntrance(skyX, skyY);

            rockets.put(newRocket.getIdOfRocket(), newRocket);
            airspace.addObjectOnSky(newRocket.getMark(), newRocket.getXPosition(), newRocket.getYPosition(), newRocket.getIdOfRocket());
            newRocket.start();
        }


        if (!inland) {
            airspace.incramentEnemiesInSky();
            airspace.setIsEnemyInSky(true);
            noFlightZoneActivate();

        }


    }


    private boolean isFileUpdated(File file) {


        long time;
        time = file.lastModified();
        if (this.timeStamp != time) {
            this.timeStamp = time;


            return true;
        }


        return false;
    }

    public static synchronized void noFlightZoneActivate() {
        Set<Integer> s = aircrafts.keySet();
        Set<Integer> s1 = rockets.keySet();
        for (Integer i : s) {
            Aircraft a = (Aircraft) aircrafts.get(i);
            if (!(a.getMark().equals("MHA") || a.getMark().equals("MBA"))) {
                a.setCanFly(false);
            }


        }
        for (Integer i : s1) {
            Rocket a = (Rocket) rockets.get(i);
            if (!a.getMark().equals("MiR"))
                a.setCanFliy(false);

        }

        stop = true;
        Airspace.setNoFly(true);


    }

    public static synchronized void noFlightZoneDeactivate() {


        stop = false;


    }

    public static boolean isStop() {
        return stop;
    }

    public static boolean isThisEnemy(int id) {


        if (id == 0) {
            return false;
        }

        boolean tmp;
        if (id < 600) {

            Aircraft a = (Aircraft) aircrafts.get(id);
            tmp = a.isEnemy();

        } else {
            Rocket r = (Rocket) rockets.get(id);
            tmp = r.isEnemy();

        }

        return tmp;

    }
}




