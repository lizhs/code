package proxy2;

/**
 * 
 * 使用方式
 */
public class TestCiglib {

	public static void main(String[] args) throws Exception {
		// 静态方法 的调用方式,ide的代码提示、效验、查找引用不太好用了 ，不过也可以对IDE的扩展进行弥补
		// 推荐非静态的方法
		BizStaticMethodCaller.callWithLog(BizCls2.class, "staticMethod1");
		BizStaticMethodCaller.callWithLog(BizCls2.class, "staticMethod3", "333");

		// 如何使非静态的方法，统一采用 动态代理的形式，所以对象应有统一的地方创建
		BizCls2 hello = CglibProxy.getProxyInstance(BizCls2.class);
		// 非静态的，通过代理来完成
		hello.method2("22");
	}
}