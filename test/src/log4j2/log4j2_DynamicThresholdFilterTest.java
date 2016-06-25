package log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.Test;

public class log4j2_DynamicThresholdFilterTest {
    static {
        //添加到插件中
        System.setProperty("log4j.configurationFile", "log4j2/log4j2_DynamicThresholdFilter.xml");
    }
    Logger logger1 = LogManager.getLogger(log4j2_DynamicThresholdFilterTest.class);

    @Test
    public void testDynamicThresholdFilter() {
        //用户1采用debug级别
        ThreadContext.put("loginId", "User1");
        logger1.debug("User1=debug1111");
        logger1.error("User1=error11111");
        //用户2采用error级别
        ThreadContext.put("loginId", "User2");
        logger1.debug("User2=debug1111");
        logger1.error("User2=error11111");
    }

}
