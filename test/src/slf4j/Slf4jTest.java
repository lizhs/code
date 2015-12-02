package slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jTest {

    static {
        System.setProperty("log4j.configurationFile", "log4j2/log4j2.xml");
    }
    static Logger logger = LoggerFactory.getLogger(Slf4jTest.class);
    public static void main(String[] args) {
        logger.info("xx");
    }

}
