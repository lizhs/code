package log4j2;

import java.io.IOException;
import java.util.UUID;

import org.apache.logging.log4j.EventLogger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.async.AsyncLogger;
import org.apache.logging.log4j.message.StructuredDataMessage;
import org.junit.Test;

import ws.client.User;

public class Log4j2Test {
    static {
        //添加到插件中
        System.setProperty("log4j.configurationFile", "log4j2/log4j2.xml");
    }
    Logger logger1 = LogManager.getLogger(Log4j2Test.class);
    Logger logger = logger1;
    private final Marker SQL_MARKER = MarkerManager.getMarker("SQL");
    private final Marker UPDATE_MARKER = MarkerManager.getMarker("SQL_UPDATE").setParents(SQL_MARKER);

    /**
     * <RegexFilter regex=".* filtertest .*" onMatch="ACCEPT"
     * onMismatch="DENY" />
     */
    @Test
    public void testSimple() {

        new MyLog(MyLog.class).info("xx");
        //        logger1.info("xx");
    }

    /**
     * 验证删除日志文件是否重新新建
     */
    @Test
    public void testDeleteLogFile() {
        Logger logger2 = LogManager.getLogger("sys.test");
        while (true) {
            try {
                System.in.read();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            logger2.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        }
    }

    @Test
    public void testDefaultRolloverStrategy() {
        Logger logger2 = LogManager.getLogger("sys.test");
        for (int i = 0; i < 10000000000000L; i++) {
            logger2.debug("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + i);
        }
    }

    @Test
    public void testFilter() {
        logger.debug("xxx filtertest xx");
        logger.debug("xxx");
    }

    static {
        Level.forName("Cust1", 550);
    }

    @Test
    public void testCustomLevel() {
        // This creates the "VERBOSE" level if it does not exist yet.
        //编码
        logger.log(Level.forName("Cust0", 550), "0000");

        //配置中定义
        int intLevel = Level.getLevel("Cust1").intLevel();
        System.out.println(intLevel);
        logger.log(Level.getLevel("Cust1"), "1111"); // throws
        logger.log(Level.getLevel("Cust2"), "222"); // throws
        logger.log(Level.getLevel("Cust3"), "3333"); // throws

        //没有定义  不会报错，直接不打印
        logger.log(Level.getLevel("Cust4"), "不存在！"); // throws
    }

    @Test
    public void testThreadContext() {
        logger.debug("Message 000");

        ThreadContext.push("11"); // Add the fishtag;
        ThreadContext.push("22"); // Add the fishtag;
        ThreadContext.put("CONSUMER_SEQ_NO", "213233333333333333333333333");

        logger.debug("Message 1");
        logger.debug("Message 2");
        ThreadContext.pop();
        ThreadContext.pop();
    }

    //    @Test
    //    public void testEventLog() {
    //        String confirm = UUID.randomUUID().toString();
    //        confirm = confirm.substring(0, 30);
    //        ThreadContext.put("hostname", "111");
    //
    //        StructuredDataMessage msg = new StructuredDataMessage(confirm, null, "transfer");
    //        msg.put("toAccount", "xxx");
    //        msg.put("fromAccount", "xx");
    //        msg.put("amount", "xx");
    //        EventLogger.logEvent(msg);
    //        logger1.debug("xx");
    //    }

    public static class Obj1 {

    }

    @Test
    public void testTrace() {//用于业务逻辑分析
        logger1.entry(new Obj1(), new Obj1());
        logger1.debug("xx");
        logger1.exit(new Obj1());
    }

    @Test
    public void testDynamicThresholdFilter() {
        ThreadContext.put("loginId", "User1");
        logger1.debug("User1=debug1111");
        logger1.error("User1=error11111");
        ThreadContext.put("loginId", "User2");
        logger1.debug("User2=debug1111");
        logger1.error("User2=error11111");
       
    }

    @Test
    public void testMarkerFilter() {
        Marker marker1 = MarkerManager.getMarker("maker1");
        logger1.debug(marker1, "1111");
        Marker marker2 = MarkerManager.getMarker("maker2");//可以继承过来
        marker2.setParents(marker1);
        logger1.debug(marker2, "222");

        Marker marker3 = MarkerManager.getMarker("maker3");
        logger1.debug(marker3, "333");
    }

    @Test
    public void testBurstFilter() {
        logger1.debug(UPDATE_MARKER, "SELECT * FROM {}", "1");
        long a = System.nanoTime();
        logger1.debug(UPDATE_MARKER, "SELECT * FROM {}", "1");
        logger1.debug(UPDATE_MARKER, "SELECT * FROM {}", "1");
        logger1.debug(UPDATE_MARKER, "SELECT * FROM {}", "1");
        long b = System.nanoTime();

        System.out.println(b - a);
        a = System.nanoTime();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
            logger1.debug(UPDATE_MARKER, "SELECT * FROM {}", i);
        long end = System.currentTimeMillis();
        b = System.nanoTime();
        System.out.println("耗时ms:" + (end - start));
        System.out.println("耗时ns:" + (b - a));

    }

}
