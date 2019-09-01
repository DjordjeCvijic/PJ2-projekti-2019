package applications;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerService {
    private static LoggerService loggerServiceInstance = null;
    private static Logger myLogger = null;

    public static LoggerService getInstance() {
        if (loggerServiceInstance == null)
            loggerServiceInstance = new LoggerService();

        return loggerServiceInstance;
    }


    private LoggerService() {
        myLogger = Logger.getLogger("MyLogger");
        FileHandler fh;

        try {
            fh = new FileHandler("src" + File.separator + "resources" + File.separator + "LoggerFile.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            myLogger.addHandler(fh);


        } catch (Exception e) {
            myLogger.log(Level.WARNING, e.toString());

        }
    }

    public static void log(Level level, Exception ex) {
        myLogger.log(level, ex.toString(), ex);

    }
}
