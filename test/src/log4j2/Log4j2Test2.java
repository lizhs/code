package log4j2;

import org.apache.logging.log4j.LogManager;

public class Log4j2Test2 {
	static {
		System.out.println("静态块Log4j2Test2");

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Load1 l = new Load1();
	}

}
