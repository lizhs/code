package log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.Test;

public class Log4j2ForLTTSTest {
    static {
        //添加到插件中
        //        System.setProperty("log4j.configurationFile", "log4j2/log4j2.xml");
        System.setProperty("ltts.log.home", "G:/logs");
        System.setProperty("ltts.vmid", "testVmid");
        System.setProperty("log4j.configurationFile", "log4j2/ltts_log_default.xml");
        System.setProperty("prcscd", "0002");
    }
    static Logger logger1 = LogManager.getLogger(Log4j2ForLTTSTest.class);
    @Test
    public void testSimple() {
        org.apache.logging.log4j.ThreadContext.put("LogType", "busi_onl");
        org.apache.logging.log4j.ThreadContext.put("prcscd", "0001");
        
        
        ThreadContext.get("");
        

        logger1.info("参数还没有传递之前的日志，SysLog切换了，交易码是不会为空的，所以这个日志通常为空，只是为了启动的时候不报错");
        logger1.info("xxx");
    }
}
