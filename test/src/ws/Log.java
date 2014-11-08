package ws;

import javax.jws.WebMethod;
public class Log {
	private String a;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	@WebMethod
	public void info(String s) {
		System.out.println(s);
	}
}
