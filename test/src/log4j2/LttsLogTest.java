package log4j2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.Test;

import exception.test;

public class LttsLogTest {
    static {
        //添加到插件中
        System.setProperty("ltts.log.home", "G:/t");//日志主目录
        System.setProperty("ltts.vmid", "subsysTest");//测试子系统
        System.setProperty("log4j.configurationFile", "log4j2/ltts_log_native.xml");
    }

    @Test
    public void test_sys_poll() throws Exception {
        Logger logger1 = LogManager.getLogger("sys.poll");
        logger1.info("轮询日志");
    }

    @Test
    public void test_busi_onl() throws IOException {
        //DynamicRollingFileAppender
        ThreadContext.put("LogType", "busi_onl");//业务联机
        ThreadContext.put("jiaoyirq", "20160625");//模拟交易日志
        Logger logger1 = LogManager.getLogger(LttsLogTest.class);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String prcscd = scanner.next();//动态产生不同交易码
            System.out.println("交易吗：" + prcscd);
            ThreadContext.put("prcscd", prcscd);
            logger1.info("User2=error11111--onl" + prcscd);//打印交易码下的运行日志
            System.out.println("ok");
        }

    }
    
    @Test
    public void test_busi_bat() throws IOException {
        //DynamicRollingFileAppender
        ThreadContext.put("LogType", "busi_bat");//业务
        ThreadContext.put("jiaoyirq", "20160625");//模拟交易日志
        ThreadContext.put("pljylcbs", "dayend");//流程
        ThreadContext.put("pljyzbsh", "001");//组
        ThreadContext.put("jobname", "job001");//组
        Logger logger1 = LogManager.getLogger(LttsLogTest.class);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String prcscd = scanner.next();//动态产生不同交易码
            System.out.println("交易吗：" + prcscd);
//            ThreadContext.put("prcscd", prcscd);
            ThreadContext.put("pljioyma",prcscd);//组
            logger1.info("User2=error11111--bat" + prcscd);//打印交易码下的运行日志
            System.out.println("ok");
        }

    }
    
    @Test
    public void test_busi_bat_missing_value() throws IOException {
        //DynamicRollingFileAppender
        ThreadContext.put("LogType", "busi_bat");//业务
        ThreadContext.put("jiaoyirq", "20160625");//模拟交易日志
        ThreadContext.put("pljylcbs", "dayend");//流程
        ThreadContext.put("pljyzbsh", "001");//组
//        ThreadContext.put("jobname", "job001");//组  如果这个没有什么效果？
        Logger logger1 = LogManager.getLogger(LttsLogTest.class);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String prcscd = scanner.next();//动态产生不同交易码
            System.out.println("交易吗：" + prcscd);
//            ThreadContext.put("prcscd", prcscd);
            ThreadContext.put("pljioyma",prcscd);//组
            logger1.info("User2=error11111--bat" + prcscd);//打印交易码下的运行日志
            System.out.println("ok");
        }
    }

}
