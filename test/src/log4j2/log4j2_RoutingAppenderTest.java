package log4j2;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.Test;

public class log4j2_RoutingAppenderTest {
    static {
        //添加到插件中
        System.setProperty("log4j.configurationFile", "log4j2/log4j2_RoutingAppender.xml");
    }

    @Test
    public void testRoutingAppender() throws IOException {
        //DynamicRollingFileAppender
        ThreadContext.put("date", "20160625");
        Logger logger1 = LogManager.getLogger("sys.tt");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String prcscd = scanner.next();
            System.out.println("交易吗：" + prcscd);
            ThreadContext.put("type", "onl");
            ThreadContext.put("prcscd", prcscd);
            logger1.info("User2=error11111--onl");
            System.out.println("ok");
        }
    }

}
