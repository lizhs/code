package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 
 * @author lizhs
 * @date 2014-3-9
 * @desc:
 */
public class HeapOOM {
	static class OOMObject {
	}

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<OOMObject>();
		while (true) {
			list.add(new OOMObject());
		}
	}

}
