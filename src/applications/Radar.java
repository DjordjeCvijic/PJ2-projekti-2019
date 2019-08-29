package applications;

import get_properties.GetRadarPropertyValues;
import model.Aircraft;
import model.Airspace;
import model.Crash;
import model.Rocket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Radar extends Thread {

    public Airspace airspace;
    public int intervalForMap;
    private int skyX;
    private int skyY;
    public static File fileMap;
    public static File fileAlert;
    public static File fileEvents;
    public static List idOfEnemis;
    private GetRadarPropertyValues properties;

    public Radar(Airspace a) {
        airspace = a;
        skyX = a.getSkyX();
        skyY = a.getSkyY();
        properties=new GetRadarPropertyValues();
        try {

            intervalForMap = Integer.parseInt(properties.getPropValue("time_interval_to_scan_sky"));


        } catch (Exception e) {
            LoggerService logger=LoggerService.getInstance();
            logger.log(Level.WARNING,e);
        }

        fileMap = new File("src" + File.separator + "resources" + File.separator + "map.txt");
        fileAlert=new File("src" + File.separator + "alert");
        fileEvents=new File("src" + File.separator + "events" );

        idOfEnemis = new ArrayList<Integer>();

    }

    public void run() {


        while (true) {

            synchronized (airspace) {

                try {
                    synchronized (fileMap) {
                        if (!Simulator.isStop() && airspace.isNoFly()) {
                            airspace.notify();
                            System.out.println("notufy u radatu poslije zabrane leta");
                            Airspace.setNoFly(false);

                        }


                        BufferedWriter out = new BufferedWriter(new PrintWriter(fileMap));
                        out.write(skyX + "#" + skyY);
                        out.write("\r\n");
                        int numOfMili = 0;

                        for (int i = 0; i < skyX; i++) {
                            for (int j = 0; j < skyY; j++) {
                                out.write(airspace.getInfo(i, j));
                                out.write("\r\n");
                                if (Simulator.isThisEnemy(airspace.getIdInThisPosition(i, j)) && !idOfEnemis.contains(airspace.getIdInThisPosition(i, j))) {
                                    synchronized (fileEvents) {
                                        idOfEnemis.add(airspace.getIdInThisPosition(i, j));
                                        try {
                                            BufferedWriter out1 = new BufferedWriter(new PrintWriter(
                                                    fileEvents + File.separator + Long.toString(System.currentTimeMillis()) + ".txt"));
                                            out1.write(airspace.getInfo(i, j));
                                            out1.close();
                                            airspace.addIdsOfEnemisAircraft(airspace.getIdInThisPosition(i, j));

                                            //System.out.println("notify za stranu letjelicu u radaru");
                                            airspace.notify();//ide u simulator
                                        } catch (Exception e) {
                                            LoggerService logger=LoggerService.getInstance();
                                            logger.log(Level.WARNING,e);
                                        }
                                    }


                                }

                            }
                        }


                        out.close();

                        if (airspace.isIsEnemyInSky() && airspace.getEnemiesInSky() == 0) {
                            airspace.setIsEnemyInSky(false);
                            //Simulator.noFlightZoneDeactivate();

                        }


                    }
                } catch (Exception e) {
                    LoggerService logger=LoggerService.getInstance();
                    logger.log(Level.WARNING,e);
                }


                if (airspace.getNumberOfAircraftInAccidents() != 0){
                    System.out.println("pozvan crash u radaru");
                    crashInAirspace();

                }


            }
            try {

                sleep(intervalForMap * 1000);
            } catch (Exception e) {
                LoggerService logger=LoggerService.getInstance();
                logger.log(Level.WARNING,e);
            }


        }

    }

    private synchronized void crashInAirspace() {
        synchronized (fileAlert) {
            String description;
            String time;
            String positionOfCrash;
            Aircraft aircraft = null;
            Rocket rocekt = null;
            Integer[] arr = airspace.getIdsOfAircraftToEleminate();
            int first = arr[0];
            int second = arr[1];
            airspace.remuveIdsOfAircraftToEleminate(arr[0]);
            airspace.remuveIdsOfAircraftToEleminate(arr[1]);
            System.out.println(first + " ucesnici "+ second);
            if (first < 600) {
                aircraft = (Aircraft) Simulator.aircrafts.get(first);
                description = aircraft.getMark()+"(id:"+aircraft.getIdOfAircraft()+")";
            } else {
                rocekt = (Rocket) Simulator.rockets.get(first);
                description = rocekt.getMark()+"(id:"+rocekt.getIdOfRocket()+")";
            }
            if (second < 600) {

                aircraft = (Aircraft) Simulator.aircrafts.get(second);
                description=description.concat(" and " + aircraft.getMark()+"(id:"+aircraft.getIdOfAircraft()+")");
                positionOfCrash = aircraft.getXPosition() + " " + aircraft.getYPosition();
            } else {
                rocekt = (Rocket) Simulator.rockets.get(second);
                description=description.concat(" and " + rocekt.getMark()+"(id:"+rocekt.getIdOfRocket()+")");
                positionOfCrash = rocekt.getXPosition() + " " + rocekt.getYPosition();
            }

            time = Long.toString(System.currentTimeMillis());

            Crash c = new Crash(description, time, positionOfCrash);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileAlert + File.separator + time + ".ser"));
                oos.writeObject(c);
                System.out.println(c.toString());
            } catch (Exception e) {
                LoggerService logger=LoggerService.getInstance();
                logger.log(Level.WARNING,e);
            }


        }

    }
}
