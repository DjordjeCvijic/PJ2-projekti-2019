package applications;

import model.*;
import model.aircrafts.Aircraft;
import model.aircrafts.helicopters.*;
import model.aircrafts.drone.Drone;
import model.aircrafts.airplanes.FireAirplane;
import model.rockets.Rocket;
import model.rockets.ice_protection_rocket.IceProtectionRocket;
import model.aircrafts.airplanes.military.*;
import model.rockets.military_rocket.MilitaryRocket;
import model.aircrafts.airplanes.*;

import java.util.*;
import java.util.logging.Level;

import get_properties.GetConfigPropertyValues;


public class Simulator extends Thread {
    private int timeInterval;
    private int skyX;
    private int skyY;
    private String[] aircraftToAdd = {"PrA", "PrH", "TrA", "TrH", "FrA", "FrH", "IPR", "Drn", "MHA", "MBA", "MiR"};
    public static Map aircrafts;
    public static Map rockets;
    private final Airspace airspace;
    private Random random = new Random();
    private static boolean stop = false;
    private GetConfigPropertyValues properties;


    public Simulator(Airspace airspace) {
        properties = new GetConfigPropertyValues();
        skyX = Integer.parseInt(properties.getPropValue("sky_width"));
        skyY = Integer.parseInt(properties.getPropValue("sky_height"));
        timeInterval = Integer.parseInt(properties.getPropValue("time_interval_to_create_aircraft"));
        airspace.setAirspace(skyX, skyY);
        this.airspace = airspace;
        aircrafts = new HashMap<Integer, Aircraft>();
        rockets = new HashMap<Integer, Rocket>();
    }


    public void run() {
        Aircraft newAircraft;
        Rocket newRocket;
        while (true) {
            synchronized (airspace) {
                if (stop && airspace.getNumberOfEnemiesAircraft() == 0) {
                    try {
                        airspace.wait();
                    } catch (Exception e) {
                        LoggerService logger = LoggerService.getInstance();
                        logger.log(Level.WARNING, e);
                    }
                }

                Integer idOfEnemy = airspace.getIdsOfEnemiesAircraft();
                if (idOfEnemy != 0) {
                    sendMilitaryAircraft(idOfEnemy);


                } else if (Integer.parseInt(properties.getPropValue("enemy_aircraft")) > 0 || Integer.parseInt(properties.getPropValue("inland_aircraft")) > 0) {
                    try {

                        int numberOfEnemiesAircrafts = Integer.parseInt(properties.getPropValue("enemy_aircraft"));
                        int numberOfInlandAircrafts = Integer.parseInt(properties.getPropValue("inland_aircraft"));
                        if (numberOfInlandAircrafts > 0) {
                            String nextAircraft = aircraftToAdd[random.nextInt(3) + 8];
                            addMilitaryAircraft(nextAircraft, true);

                        } else if (numberOfEnemiesAircrafts > 0) {
                            String nextAircraft = aircraftToAdd[random.nextInt(3) + 8];
                            addMilitaryAircraft(nextAircraft, false);

                        }

                    } catch (Exception e) {
                        LoggerService logger = LoggerService.getInstance();
                        logger.log(Level.WARNING, e);
                    }
                } else {


                    String nextAircraft = aircraftToAdd[random.nextInt(8)];
                    if ("PrA".equals(nextAircraft)) {
                        newAircraft = new PassengerAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(2) + 1, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextInt(200) + 1, random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("PrH".equals(nextAircraft)) {
                        newAircraft = new PassengerHelicopter(Integer.toString(random.nextInt(1000)), random.nextInt(2) + 1, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextInt(200) + 1, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("TrA".equals(nextAircraft)) {
                        newAircraft = new TransportAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(2) + 1, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("TrH".equals(nextAircraft)) {
                        newAircraft = new TransportHelicopter(Integer.toString(random.nextInt(1000)), random.nextInt(2) + 1, new HashMap<Integer, Object>(), new ArrayList(),
                                "Prtljaga", random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("FrA".equals(nextAircraft)) {
                        newAircraft = new FireAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(2) + 1, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("FrH".equals(nextAircraft)) {
                        newAircraft = new FireHelicopter(Integer.toString(random.nextInt(1000)), random.nextInt(2) + 1, new HashMap<Integer, Object>(), new ArrayList(),
                                random.nextDouble() * 100, airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();

                    } else if ("IPR".equals(nextAircraft)) {
                        newRocket = new IceProtectionRocket(random.nextDouble() * 100, random.nextInt(2) + 1, airspace);
                        newRocket.setEntrance(skyX, skyY);

                        rockets.put(newRocket.getIdOfRocket(), newRocket);
                        airspace.addObjectOnSky(newRocket.getMark(), newRocket.getXPosition(), newRocket.getYPosition(), newRocket.getIdOfRocket());
                        newRocket.start();

                    } else if ("Drn".equals(nextAircraft)) {
                        newAircraft = new Drone(Integer.toString(random.nextInt(1000)), random.nextInt(2) + 1, new HashMap<Integer, Object>(), new ArrayList(),
                                airspace);
                        newAircraft.setEntrance(skyX, skyY);

                        aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
                        airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                        newAircraft.start();
                    }


                }

            }
            try {
                sleep(timeInterval * 1000);
            } catch (Exception e) {
                LoggerService logger = LoggerService.getInstance();
                logger.log(Level.WARNING, e);
            }
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
            if (!inland) newAircraft.setFlightSpeed(random.nextInt(1) + 2);
            newAircraft.start();
        } else if ("MBA".equals(mark)) {
            Aircraft newAircraft = new MilitaryBomberAirplane(Integer.toString(random.nextInt(1000)), random.nextInt(100), new HashMap<Integer, Object>(), new ArrayList(),
                    inland, airspace);
            newAircraft.setEntrance(skyX, skyY);
            aircrafts.put(newAircraft.getIdOfAircraft(), newAircraft);
            airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
            if (!inland) newAircraft.setFlightSpeed(random.nextInt(1) + 2);

            newAircraft.start();

        } else {
            Rocket newRocket = new MilitaryRocket(random.nextDouble() * 100, random.nextInt(100), inland, airspace);
            newRocket.setEntrance(skyX, skyY);

            rockets.put(newRocket.getIdOfRocket(), newRocket);
            airspace.addObjectOnSky(newRocket.getMark(), newRocket.getXPosition(), newRocket.getYPosition(), newRocket.getIdOfRocket());
            if (!inland) newRocket.setFlightSpeed(random.nextInt(1) + 2);
            newRocket.start();
        }


        if (!inland) {
            airspace.incrementEnemiesInSky();
            airspace.setIsEnemyInSky(true);
            noFlightZoneActivate();
        }
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
                a.setCanFly(false);
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




