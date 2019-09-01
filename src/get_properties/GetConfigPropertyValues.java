package get_properties;

import applications.LoggerService;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;

public class GetConfigPropertyValues {
    private String result = " ";
    private String propertyFileName = "src" + File.separator + "resources" + File.separator + "config.properties";

    public GetConfigPropertyValues() {

    }

    public String getPropValue(String propertyName) {
        try {
            Properties property = new Properties();
            BufferedReader br = new BufferedReader(new FileReader(propertyFileName));
            property.load(br);
            result = property.getProperty(propertyName);
            br.close();

        } catch (Exception e) {
            LoggerService logger = LoggerService.getInstance();
            logger.log(Level.WARNING, e);

        }
        return result;

    }


}
