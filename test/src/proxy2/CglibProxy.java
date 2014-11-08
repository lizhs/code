package proxy2;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 动态代理只能处理 非静态的方法，非静态的方法通过该形式进行处理
 */
public class CglibProxy implements MethodInterceptor {
	static CglibProxy instence=new CglibProxy();
	
	public static <T>  T getProxyInstance(Class<T> type) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(type);
		enhancer.setCallback(instence);
		return (T) enhancer.create();
	}

	@Override
	public Object intercept(final Object target, Method method, final Object[] args, final MethodProxy proxy)
			throws Throwable {
		System.out.println("前处理日志打印,参数" + args);
		Object result = proxy.invokeSuper(target, args);
		System.out.println("后处理日志打印");
		return result;
	}
}