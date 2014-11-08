package jvm;
public class A {
	static {
		System.out.println("A初始化");
	}
	//如果是final javac会把用到s2的地方替换成了“2222222”字符串
	public final static String s2 = "2222222";
	//作用引用处理，所以需要加载该Class
	public static String s1 = "111111";
}
