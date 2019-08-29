package applications;

import model.*;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

import get_properties.GetConfigPropertyValues;

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
    private GetConfigPropertyValues properties;


    public Simulator(Airspace a) {


        properties = new GetConfigPropertyValues();
        skyX = Integer.parseInt(properties.getPropValue("sky_width"));
        System.out.println(skyX);
        skyY = Integer.parseInt(properties.getPropValue("sky_height"));


        // System.out.println("construktor simulatora,dimenzije:"+skyX+" "+skyY);//mmmmmmmmmmmmmmmmmm

        timeInterval = Integer.parseInt(properties.getPropValue("time_interval_to_create_aircraft"));


        a.setAirspace(skyX, skyY);
        airspace = a;
        aircrafts = new HashMap<Integer, Aircraft>();
        rockets = new HashMap<Integer, Rocket>();


    }


    public void run() {

        Aircraft newAircraft = null;
        Rocket newRocket = null;

        while (true) {
            synchronized (airspace) {

                if (stop && airspace.getNumberOfEnemisAircraft() == 0) {
                    try {
                        System.out.println("wait u simulatoru");
                        airspace.wait();

                    } catch (Exception e) {
                        LoggerService logger = LoggerService.getInstance();
                        logger.log(Level.WARNING, e);
                    }
                }

                Integer idOfEnemy = airspace.getIdsOfEnemisAircraft();
                if (idOfEnemy != 0) {
                    System.out.println("id neprijatelja: " + idOfEnemy);
                    sendMilitaryAircraft(idOfEnemy);


                } else if (Integer.parseInt(properties.getPropValue("enemy_aircraft")) > 0 || Integer.parseInt(properties.getPropValue("inland_aircraft")) > 0) {
                    try {

                        numberOfEnemiesAircrafts = Integer.parseInt(properties.getPropValue("enemy_aircraft"));
                        numberOfInlandAircrafts = Integer.parseInt(properties.getPropValue("inland_aircraft"));
                        //int tmp1 = numberOfEnemiesAircrafts;
                        //int tmp2=numberOfInlandAircrafts;

                        if (numberOfInlandAircrafts > 0) {
                            String nextAircraft = aircraftToAdd[random.nextInt(3) + 8];

                            addMilitaryAircraft(nextAircraft, true);


                        } else if (numberOfEnemiesAircrafts > 0) {
                            String nextAircraft = aircraftToAdd[random.nextInt(3) + 8];
                            addMilitaryAircraft(nextAircraft, false);


                        }


                        // timeStamp = f.lastModified();

                    } catch (Exception e) {
                        LoggerService logger = LoggerService.getInstance();
                        logger.log(Level.WARNING, e);
                    }
                } else {


                    String nextAircraft = aircraftToAdd[random.nextInt(8)];
                    if ("PrA".equals(nextAircraft)) {
                        newAircraft = new PessengerAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(2)+1, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextInt(200) + 1, random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("PrH".equals(nextAircraft)) {
                        newAircraft = new PessengerHelicopter(Integer.toString(random.nextInt(1000)), random.nextInt(2)+1, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextInt(200) + 1, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("TrA".equals(nextAircraft)) {
                        newAircraft = new TransportAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(2)+1, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("TrH".equals(nextAircraft)) {
                        newAircraft = new TransportHelicopter(Integer.toString(random.nextInt(1000)), random.nextInt(2)+1, new HashMap<Integer, Object>(), new ArrayList(),
                                "Prtljaga", random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("FrA".equals(nextAircraft)) {
                        newAircraft = new FireAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(2)+1, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("FrH".equals(nextAircraft)) {
                        newAircraft = new FireHelicopter(Integer.toString(random.nextInt(1000)), random.nextInt(2)+1, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("IPR".equals(nextAircraft)) {
                        newRocket = new IceProtectionRocket(random.nextDouble() * 100, random.nextInt(2)+1, airspace);
                        newRocket.setEntrance(skyX, skyY);

                        rockets.put(newRocket.getIdOfRocket(), newRocket);
                        airspace.addObjectOnSky(newRocket.getMark(), newRocket.getXPosition(), newRocket.getYPosition(), newRocket.getIdOfRocket());
                        newRocket.start();

                    } else if ("Drn".equals(nextAircraft)) {
                        newAircraft = new Drone(Integer.toString(random.nextInt(1000)), random.nextInt(2)+1, new HashMap<Integer, Object>(), new ArrayList(),
                                airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();
                    }


                }

            }
            try {
                System.out.println("sleep u simulatoru");
                //airspace.wait(timeInterval * 1000);
                sleep(timeInterval * 1000);
            } catch (Exception e) {
                LoggerService logger = LoggerService.getInstance();
                logger.log(Level.WARNING, e);
            }


            //airspace.print(skyX,skyY);

        }


    }

    private synchronized void sendMilitaryAircraft(Integer idOfEnemy) {


        if (idOfEnemy < 600) {
            Aircraft objectToAttack = (Aircraft) aircrafts.get(idOfEnemy);

            Aircraft newAircraftForAttack = new MilitaryHunterAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(100), new HashMap<Integer, Object>(), new ArrayList(),
                    true, airspace);
            newAircraftForAttack.setEntranceForAttack(skyX, skyY, objectToAttack.getFlightIndex(), objectToAttack.getXPosition(), objectToAttack.getYPosition(), 1);
            newAircraftForAttack.setInAttack(true);
            newAircraftForAttack.setIdToAttack(idOfEnemy);
            aircrafts.put(newAircraftForAttack.getIdOfAircraft(), newAircraftForAttack);
            airspace.addObjectOnSky(newAircraftForAttack.getMark(), newAircraftForAttack.getXPosition(), newAircraftForAttack.getYPosition(), newAircraftForAttack.getIdOfAircraft());
            newAircraftForAttack.start();


            Aircraft newAircraftForAttack1 = new MilitaryHunterAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(100), new HashMap<Integer, Object>(), new ArrayList(),
                    true, airspace);
            newAircraftForAttack1.setEntranceForAttack(skyX, skyY, objectToAttack.getFlightIndex(), objectToAttack.getXPosition(), objectToAttack.getYPosition(), 2);
            newAircraftForAttack1.setInAttack(true);
            newAircraftForAttack1.setIdToAttack(idOfEnemy);
            aircrafts.put(newAircraftForAttack1.getIdOfAircraft(), newAircraftForAttack1);
            airspace.addObjectOnSky(newAircraftForAttack1.getMark(), newAircraftForAttack1.getXPosition(), newAircraftForAttack1.getYPosition(), newAircraftForAttack1.getIdOfAircraft());
            newAircraftForAttack1.start();
            System.out.println("id od lovaca: " + newAircraftForAttack.getIdOfAircraft() + " i " + newAircraftForAttack1.getIdOfAircraft());
        } else {
            Rocket objectToAttack = (Rocket) rockets.get(idOfEnemy);

            Aircraft newAircraftForAttack = new MilitaryHunterAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(100), new HashMap<Integer, Object>(), new ArrayList(),
                    true, airspace);
            newAircraftForAttack.setEntranceForAttack(skyX, skyY, objectToAttack.getFlightIndex(), objectToAttack.getXPosition(), objectToAttack.getYPosition(), 1);
            newAircraftForAttack.setInAttack(true);
            newAircraftForAttack.setIdToAttack(idOfEnemy);
            aircrafts.put(newAircraftForAttack.getIdOfAircraft(), newAircraftForAttack);
            airspace.addObjectOnSky(newAircraftForAttack.getMark(), newAircraftForAttack.getXPosition(), newAircraftForAttack.getYPosition(), newAircraftForAttack.getIdOfAircraft());
            newAircraftForAttack.start();


            Aircraft newAircraftForAttack1 = new MilitaryHunterAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(100), new HashMap<Integer, Object>(), new ArrayList(),
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
            Aircraft newAircraft = new MilitaryHunterAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(100), new HashMap<Integer, Object>(), new ArrayList(),
                    inland, airspace);
            newAircraft.setEntrance(skyX, skyY);
            aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
            airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
            if(!inland)newAircraft.setFlightSpeed(random.nextInt(1)+2);
            newAircraft.start();
        } else if ("MBA".equals(mark)) {
            Aircraft newAircraft = new MilitaryBomberAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(100), new HashMap<Integer, Object>(), new ArrayList(),
                    inland, airspace);
            newAircraft.setEntrance(skyX, skyY);
            aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
            airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
            if(!inland)newAircraft.setFlightSpeed(random.nextInt(1)+2);

            newAircraft.start();

        } else {
            Rocket newRocket = new MilitaryRocket(random.nextDouble() * 100, random.nextInt(100), inland, airspace);
            newRocket.setEntrance(skyX, skyY);

            rockets.put(newRocket.getIdOfRocket(), newRocket);
            airspace.addObjectOnSky(newRocket.getMark(), newRocket.getXPosition(), newRocket.getYPosition(), newRocket.getIdOfRocket());
            if(!inland)newRocket.setFlightSpeed(random.nextInt(1)+2);

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




