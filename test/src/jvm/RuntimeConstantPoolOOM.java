package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author lizhs
 * @date 2014-3-9
 * @desc:
 */
public class RuntimeConstantPoolOOM {

	/**
	 * -XX:PermSize=1m -XX:MaxPermSize=1m
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		int i = 0;
		while (true) {
			// java.lang.String.intern()作用？
			list.add(String.valueOf(i++).intern());
		}

	}
}
