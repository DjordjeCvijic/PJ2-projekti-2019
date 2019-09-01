package applications;

import get_properties.GetRadarPropertyValues;
import model.aircrafts.Aircraft;
import model.Airspace;
import model.Crash;
import model.rockets.Rocket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Radar extends Thread {

    private final Airspace airspace;
    private int intervalForMap;
    private int skyX;
    private int skyY;
    public static File fileMap;
    public static File fileAlert;
    public static File fileEvents;
    public static List idOfEnemies;

    public Radar(Airspace a) {
        airspace = a;
        skyX = a.getSkyX();
        skyY = a.getSkyY();
        GetRadarPropertyValues properties = new GetRadarPropertyValues();
        try {
            intervalForMap = Integer.parseInt(properties.getPropValue("time_interval_to_scan_sky"));
        } catch (Exception e) {
            LoggerService logger = LoggerService.getInstance();
            logger.log(Level.WARNING, e);
        }

        fileMap = new File("src" + File.separator + "resources" + File.separator + "map.txt");
        fileAlert = new File("src" + File.separator + "alert");
        fileEvents = new File("src" + File.separator + "events");
        idOfEnemies = new ArrayList<Integer>();

    }

    public void run() {
        while (true) {
            synchronized (airspace) {

                try {
                    synchronized (fileMap) {
                        if (!Simulator.isStop() && airspace.isNoFly()) {
                            airspace.notify();
                            Airspace.setNoFly(false);

                        }
                        BufferedWriter out = new BufferedWriter(new PrintWriter(fileMap));
                        out.write(skyX + "#" + skyY);
                        out.write("\r\n");

                        for (int i = 0; i < skyX; i++) {
                            for (int j = 0; j < skyY; j++) {
                                out.write(airspace.getInfo(i, j));
                                out.write("\r\n");
                                if (Simulator.isThisEnemy(airspace.getIdInThisPosition(i, j)) && !idOfEnemies.contains(airspace.getIdInThisPosition(i, j))) {
                                    synchronized (fileEvents) {
                                        idOfEnemies.add(airspace.getIdInThisPosition(i, j));
                                        try {
                                            BufferedWriter out1 = new BufferedWriter(new PrintWriter(
                                                    fileEvents + File.separator + Long.toString(System.currentTimeMillis()) + ".txt"));
                                            out1.write(airspace.getInfo(i, j));
                                            out1.close();
                                            airspace.addIdsOfEnemiesAircraft(airspace.getIdInThisPosition(i, j));
                                            airspace.notify();
                                        } catch (Exception e) {
                                            LoggerService logger = LoggerService.getInstance();
                                            logger.log(Level.WARNING, e);
                                        }
                                    }


                                }

                            }
                        }
                        out.close();

                        if (airspace.isIsEnemyInSky() && airspace.getEnemiesInSky() == 0) {
                            airspace.setIsEnemyInSky(false);
                        }


                    }
                } catch (Exception e) {
                    LoggerService logger = LoggerService.getInstance();
                    logger.log(Level.WARNING, e);
                }

                if (airspace.getNumberOfAircraftInAccidents() != 0) {
                    crashInAirspace();
                }
            }
            try {

                sleep(intervalForMap * 1000);
            } catch (Exception e) {
                LoggerService logger = LoggerService.getInstance();
                logger.log(Level.WARNING, e);
            }


        }

    }

    private synchronized void crashInAirspace() {
        synchronized (fileAlert) {
            String description;
            String time;
            String positionOfCrash;
            Aircraft aircraft;
            Rocket rocket;
            Integer[] arr = airspace.getIdsOfAircraftToEliminate();
            int first = arr[0];
            int second = arr[1];
            airspace.removeIdsOfAircraftToEliminate(arr[0]);
            airspace.removeIdsOfAircraftToEliminate(arr[1]);
            if (first < 600) {
                aircraft = (Aircraft) Simulator.aircrafts.get(first);
                description = aircraft.getMark() + "(id:" + aircraft.getIdOfAircraft() + ")";
            } else {
                rocket = (Rocket) Simulator.rockets.get(first);
                description = rocket.getMark() + "(id:" + rocket.getIdOfRocket() + ")";
            }
            if (second < 600) {

                aircraft = (Aircraft) Simulator.aircrafts.get(second);
                description = description.concat(" and " + aircraft.getMark() + "(id:" + aircraft.getIdOfAircraft() + ")");
                positionOfCrash = aircraft.getXPosition() + " " + aircraft.getYPosition();
            } else {
                rocket = (Rocket) Simulator.rockets.get(second);
                description = description.concat(" and " + rocket.getMark() + "(id:" + rocket.getIdOfRocket() + ")");
                positionOfCrash = rocket.getXPosition() + " " + rocket.getYPosition();
            }

            time = Long.toString(System.currentTimeMillis());

            Crash c = new Crash(description, time, positionOfCrash);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileAlert + File.separator + time + ".ser"));
                oos.writeObject(c);
                oos.close();
            } catch (Exception e) {
                LoggerService logger = LoggerService.getInstance();
                logger.log(Level.WARNING, e);
            }


        }

    }
}
