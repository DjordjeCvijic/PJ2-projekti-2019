package get_properties;

import applications.LoggerService;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;

public class GetRadarPropertyValues {

    private String result = " ";
    private String propertyFileName = "src" + File.separator + "resources" + File.separator + "radar.properties";
    private BufferedReader br;

    public GetRadarPropertyValues() {
    }

    public String getPropValue(String propertyName) {
        try {
            Properties prop = new Properties();
            br = new BufferedReader(new FileReader(propertyFileName));
            System.out.println(br.readLine());
            prop.load(br);
            result = prop.getProperty(propertyName);

            br.close();


        } catch (Exception e) {
            LoggerService logger = LoggerService.getInstance();
            logger.log(Level.WARNING, e);

        }
        return result;

    }
}
