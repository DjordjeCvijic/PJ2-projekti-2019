package applications;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerService {
    private static LoggerService loggerServiceInstance = null;
    private LoggerService(){
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        try {
            fh = new FileHandler("resources" + File.separator + "LoggerService.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            throw new Exception();
        } catch (Exception e) {
            logger.log(Level.WARNING,e.toString());
        }

        logger.info("Hi How r u?");

    }
}
