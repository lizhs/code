package pkg;

import java.lang.annotation.Annotation;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String pkgName = "pkg";
		Package pkg = Package.getPackage(pkgName);
		// 获得包上的注解
		Annotation[] annotations = pkg.getAnnotations();
		// 遍历注解数组
		for (Annotation an : annotations) {
			System.out.println(an);
			if (an instanceof Annation1) {
				Annation1 a=(Annation1) an;
				System.out.println("Hi,I'm the Annation1"+a.name());

			}
		}
		System.out.println("xx");
	}

}
