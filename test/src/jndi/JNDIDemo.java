package jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDIDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Context ctx = new InitialContext();
			Object datasourceRef = ctx.lookup("java:MySqlDS"); // 引用数据源
			System.out.println(datasourceRef);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
