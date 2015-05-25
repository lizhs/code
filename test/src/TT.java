import java.security.AccessController;

import sun.security.action.GetPropertyAction;

public class TT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("sun.rmi.transport.tcp.logLevel2", "1113");
		String value = System.getProperty("sun.rmi.transport.tcp.logLevel2");// AccessController.doPrivileged(new
																			// GetPropertyAction("sun.rmi.transport.tcp.logLevel"));
		System.out.println(value);

	}
}
