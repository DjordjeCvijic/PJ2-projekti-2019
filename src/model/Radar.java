package model;

import java.io.*;

public class Radar extends Thread {

    public Airspace airspace;
    public int intervalForMap;
    private int skyX;
    private int skyY;
    public static File f;

    public Radar(Airspace a) {
        airspace = a;
        skyX = a.getSkyX();
        skyY = a.getSkyY();
        try {
            BufferedReader in = new BufferedReader(new FileReader("src" + File.separator + "resources" + File.separator + "radar.properties.txt"));
            intervalForMap = Integer.parseInt(in.readLine());
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        f=new File("src" + File.separator + "resources" + File.separator + "map.txt");

    }

    public void run() {


        while (true) {

            synchronized (airspace) {

                try {
                    synchronized (f) {
                        if(!Simulator.isStop() ){
                            airspace.notify();


                        }


                        BufferedWriter out = new BufferedWriter(new PrintWriter(f));
                        out.write(skyX+"#"+skyY);
                        out.write("\r\n");

                        for (int i = 0; i < skyX; i++) {
                            for (int j = 0; j < skyY; j++) {
                                out.write(airspace.getInfo(i, j));
                                out.write("\r\n");//ovjde nisam provjerio
                            }
                        }


                        out.close();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
            try {

                sleep(intervalForMap * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}
