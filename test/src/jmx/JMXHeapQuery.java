package jmx;

import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXHeapQuery {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		/*
		 * host: 远程机器的ip地址 port: 远程java进程运行的jmxremote端口
		 */
		try {

			JMXServiceURL serviceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + args[0] + ":" + args[1]
					+ "/jmxrmi");
			JMXConnector conn = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection mbs = conn.getMBeanServerConnection();

			// 获取远程memorymxbean
			MemoryMXBean memBean = ManagementFactory.newPlatformMXBeanProxy(mbs, ManagementFactory.MEMORY_MXBEAN_NAME,
					MemoryMXBean.class);
			// 获取远程opretingsystemmxbean
			OperatingSystemMXBean newPlatformMXBeanProxy = ManagementFactory.newPlatformMXBeanProxy(mbs,
					ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
			// System.out.println(newPlatformMXBeanProxy);

			/** Collect data every 5 seconds */
			// try {
			// TimeUnit.SECONDS.sleep(5);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			MemoryUsage heap = memBean.getHeapMemoryUsage();

			long used = heap.getUsed();// 堆使用的大小
			System.out.print("used:" + used / 1024 / 1024 + "m ");

			long max = heap.getMax();
			System.out.print("max：" + max / 1024 / 1024 + "m ");

			long committed = heap.getCommitted();
			System.out.print("committed：" + committed / 1024 / 1024 + "m ");

			long init = heap.getInit();
			System.out.print("init：" + init / 1024 / 1024 + "m");
			System.out.println();
			System.exit(2);

		} catch (Exception e) {
			reportException(e);
		}

	}

	private static void reportException(Exception ex) {
		ex.printStackTrace(System.out);
	}

}
