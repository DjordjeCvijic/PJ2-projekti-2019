package applications;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SystemCopy extends Thread {
    private File fileMap;
    private File fileEvents;

    public SystemCopy() {
        fileMap = Radar.fileMap;
        fileEvents = Radar.fileEvents;

    }

    public void run() {
        while (true) {
            try {
                sleep(60000);

                Date date = new Date();
                System.out.println(date);
                String[] tmp = date.toString().split(" ");
                String[] tmp1 = tmp[3].split(":");
                String name = "backup_" + tmp[5] + "_" + tmp[1] + "_" + tmp[2] + "_" + tmp1[0] + "_" + tmp1[1] + ".zip";
                System.out.println(name);
                String[] filesInEvents = fileEvents.list();
                FileOutputStream fos = new FileOutputStream("src" + File.separator + "system_backup" + File.separator + name);
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                synchronized (fileMap) {

                    File fileToZip = fileMap;
                    FileInputStream fis = new FileInputStream(fileToZip);
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    fis.close();
                }
                synchronized (fileEvents) {

                    for (String srcFile1 : filesInEvents) {
                        File fileToZip1 = new File("src" + File.separator + "events" + File.separator + srcFile1);
                        FileInputStream fis1 = new FileInputStream(fileToZip1);
                        ZipEntry zipEntry1 = new ZipEntry(fileToZip1.getName());
                        zipOut.putNextEntry(zipEntry1);

                        byte[] bytes1 = new byte[1024];
                        int length1;
                        while ((length1 = fis1.read(bytes1)) >= 0) {
                            zipOut.write(bytes1, 0, length1);
                        }
                        fis1.close();
                    }
                    zipOut.close();

                    fos.close();
                }




            } catch (Exception e) {
                LoggerService logger=LoggerService.getInstance();
                logger.log(Level.WARNING,e);            }
        }

    }

}
