package jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class JmxTest {
	/**
	 * @param args
	 * @throws NullPointerException 
	 * @throws MalformedObjectNameException 
	 * @throws NotCompliantMBeanException 
	 * @throws MBeanRegistrationException 
	 * @throws InstanceAlreadyExistsException 
	 */

 

	public static void main(String[] args) throws MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
		System.out.println(platformMBeanServer.getClass());

		ObjectName helloName = new ObjectName("zhenghongqiang:name=HelloWorld");
		register(new Hello(), "xx", Hello.class.getSimpleName());
		mbs.registerMBean(new Hello(), helloName);
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final String MBEAN_NAME = "java.lang:";
	private static MBeanServer mbs;

	public static ObjectName getMBeanName(String type, String name) {
		String oname = MBEAN_NAME;
		if (type == null)
			throw new IllegalArgumentException("type is null.");
		oname += "type=" + type;
		if (name != null)
			oname += ",name=" + name;
		try {
			System.out.println("ObjectName="+oname);
			return new ObjectName(oname);
		} catch (MalformedObjectNameException e) {
			throw new IllegalArgumentException("Internal error!!!", e);
		}
	}

	public static void unregister(String type, String name) {

		if (mbs == null) {
			mbs = ManagementFactory.getPlatformMBeanServer();
		}
		ObjectName oname = getMBeanName(type, name);
		try {
			mbs.unregisterMBean(oname);
		} catch (Exception e) {
			throw new RuntimeException("Error to register MBean:" + name, e);
		}
	}

	public static void register(Object mbean, String type, String name) {
		if (mbs == null) {
			mbs = ManagementFactory.getPlatformMBeanServer();
		}
		ObjectName oname = getMBeanName(type, name);
		try {
			mbs.registerMBean(mbean, oname);
		} catch (InstanceAlreadyExistsException e) {
			throw new RuntimeException("MBean has bean registered:" + name, e);
		} catch (Exception e) {
			throw new RuntimeException("Error to register MBean:" + name, e);
		}
	}

}
