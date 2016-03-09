package log4j2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.ExtendedLogger;

public class MyLog {
    ExtendedLogger logger1;

    public MyLog(Class type) {
        logger1 = (ExtendedLogger) LogManager.getLogger(type);
    }

    public MyLog(Logger logger1) {
        this.logger1 = (ExtendedLogger) logger1;
    }

    public void info(String string) {
        //        logger1.info(string);
        logger1.logIfEnabled(MyLog.class+"", Level.INFO, null, string);
    }

}
