import java.util.HashMap;
import java.util.Map;

public class AndOpTest {
	static String s = "a" + "b";
	static String s2 = "a" + "b" + s + AndOpTest.class.getName();
	static String s3 = s2 + s;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int ret = 105330 & 100;
		System.out.println(ret);
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 100; i++) {
			map.put(i + "", "xx");
		}

		System.out.println(s);
		System.out.println(s2);
		System.out.println(map);
	}

}
