package log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Load1 {
    static {
        System.setProperty("ltts.home", "G:/t");
        System.setProperty("rmb.dcn.no", "A00");
        System.setProperty("rmb.system.id", "3009");

        System.setProperty("log4j.configurationFile", "log4j2/ltts_log.xml");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Logger logger2 = LogManager.getLogger("onl_busi");
        for (int i = 0; i < 1000000000; i++)
            logger2.info("xx");
    }

}
