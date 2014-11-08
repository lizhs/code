package proxy2;

/**
 *   
 * 如何对如下方法，统一添加一个前后处理的逻辑呢？
 * 有静态的、有非静态的？如何统一处理？
 */
public class BizCls2 {

	public static void staticMethod1() {
		System.out.println("处理方法1");
	}

	public static void staticMethod3(String args) {
		System.out.println("处理方法3"+args);
	}

	public void method2(String args) {
		System.out.println("处理方法2"+args);
	}
}
