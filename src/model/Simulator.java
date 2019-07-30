package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Simulator extends Thread {
    public int timeInterval;
    private int skyX;
    private int skyY;
    private String[] aircraftToAdd = {"PrA", "PrH", "TrA", "TrH", "FrA", "FrH", "IPR", "Drn", "MHA", "MBA", "MiR"};
    private Map aircrafts;
    private Map rockets;
    private final Airspace airspace;
    private long timeStamp;
    private Random random=new Random();


    public Simulator(Airspace a) {
        try {
            BufferedReader in = new BufferedReader(new FileReader("src" + File.separator + "resources" + File.separator + "config.properties.txt"));
            String s = in.readLine();
            s = in.readLine();
            String[] tmp = s.split("#");
            skyX = Integer.parseInt(tmp[0]);
            skyY = Integer.parseInt(tmp[1]);
            s = in.readLine();
            tmp = s.split("#");
            timeInterval = Integer.parseInt(tmp[0]);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(skyX+" "+skyY);
        a.setAirspace(skyX,skyY);
        airspace=a;
        aircrafts=new HashMap<Integer,Aircraft>();
        rockets=new HashMap<Integer,Rocket>();


    }






    public void run(){
        File f = new File("src" + File.separator + "resources" + File.separator + "config.properties.txt");

        this.timeStamp=f.lastModified();
        while(true){

            if(isFileUpdated(f)){
                System.out.println("izmjena");//treba ubaciti domacu ili stranu vojnu letjelicu
            }
            String nextAircraft=randomAircraft();
            Aircraft newAircraft;
            Rocket newRocket;

            synchronized (airspace) {

                if ("PrA".equals(nextAircraft)) {
                    newAircraft = new PessengerAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                            random.nextInt(200) + 1, random.nextDouble() * 100, airspace);
                    newAircraft.setEntrance(skyX, skyY);

                    aircrafts.put(newAircraft.getId(), newAircraft);
                    airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                    newAircraft.start();

                } else if ("PrH".equals(nextAircraft)) {
                    newAircraft = new PessengerHelicopter(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                            random.nextInt(200) + 1, airspace);
                    newAircraft.setEntrance(skyX, skyY);

                    aircrafts.put(newAircraft.getId(), newAircraft);
                    airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                    newAircraft.start();

                } else if ("TrA".equals(nextAircraft)) {
                    newAircraft = new TransportAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                            random.nextDouble() * 100, airspace);
                    newAircraft.setEntrance(skyX, skyY);

                    aircrafts.put(newAircraft.getId(), newAircraft);
                    airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                    newAircraft.start();

                } else if ("TrH".equals(nextAircraft)) {
                    newAircraft = new TransportHelicopter(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                            "Prtljaga", random.nextDouble() * 100, airspace);
                    newAircraft.setEntrance(skyX, skyY);

                    aircrafts.put(newAircraft.getId(), newAircraft);
                    airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                   newAircraft.start();

                } else if ("FrA".equals(nextAircraft)) {
                    newAircraft = new FireAirplane(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                            random.nextDouble() * 100, airspace);
                    newAircraft.setEntrance(skyX, skyY);

                    aircrafts.put(newAircraft.getId(), newAircraft);
                    airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                    newAircraft.start();

                } else if ("FrH".equals(nextAircraft)) {
                    newAircraft = new FireHelicopter(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                            random.nextDouble() * 100, airspace);
                    newAircraft.setEntrance(skyX, skyY);

                    aircrafts.put(newAircraft.getId(), newAircraft);
                    airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                    newAircraft.start();

                } else if ("IPR".equals(nextAircraft)) {
                    newRocket = new IceProtectionRocket(random.nextDouble() * 100, random.nextDouble() * 100, airspace);
                    newRocket.setEntrance(skyX, skyY);

                    rockets.put(newRocket.getId(), newRocket);
                    airspace.addObjectOnSky(newRocket.getMark(), newRocket.getXPosition(), newRocket.getYPosition(), newRocket.getIdOfRocket());
                    newRocket.start();

                } else if ("Drn".equals(nextAircraft)) {
                    newAircraft = new Drone(Integer.toString(random.nextInt(1000)), random.nextDouble() * 100, new HashMap<Integer, Object>(), new ArrayList(),
                            airspace);
                    newAircraft.setEntrance(skyX, skyY);

                    aircrafts.put(newAircraft.getId(), newAircraft);
                    airspace.addObjectOnSky(newAircraft.getMark(), newAircraft.getXPosition(), newAircraft.getYPosition(), newAircraft.getIdOfAircraft());
                    newAircraft.start();

                }

            }

           //airspace.print(skyX,skyY);
            try {

                sleep(timeInterval * 1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String randomAircraft(){

        return aircraftToAdd[random.nextInt(8)];
    }

    private boolean isFileUpdated(File file) {

        long time;
        time=file.lastModified();
        if (this.timeStamp != time) {
            this.timeStamp = time;
            return true;
        }


        return false;
    }

}