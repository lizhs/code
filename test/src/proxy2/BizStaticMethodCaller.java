package proxy2;

import org.apache.commons.lang3.reflect.MethodUtils;

/**
 * 静态方法通过硬编码的形式来实现
 */
public class BizStaticMethodCaller {
	public static <T> T callWithLog(Class<?> type, String method, Object... parms) throws Exception {
		System.out.println("前处理日志打印，参数"+parms);
		Object ret = MethodUtils.invokeStaticMethod(type, method, parms);
		System.out.println("后处理日志打印");
		return (T) ret;
	}
}
