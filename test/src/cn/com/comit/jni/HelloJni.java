package cn.com.comit.jni;

import java.lang.reflect.Field;

public class HelloJni {

	public native void displayHelloJni();

	/**
	 * @param args
	 * @throws Exception
	 * @throws SecurityException
	 */
	public static void main(String[] args) throws SecurityException, Exception {
		System.setProperty("java.library.path", System.getProperty("java.library.path") + ";./c");
		Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
		fieldSysPath.setAccessible(true);
		fieldSysPath.set(null, null);

		System.loadLibrary("hello");
//		System.load("hello.dll");
		new HelloJni().displayHelloJni();
	}

}