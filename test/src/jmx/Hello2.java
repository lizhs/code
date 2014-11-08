package jmx;
public class Hello2 implements HelloMBean {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void printHello() {
		System.out.println("Hello World222, " + name);
	}

	public void printHello(String whoName) {
		System.out.println("Hello222 , " + whoName);
	}
}
