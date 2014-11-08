package rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import rmi.HelloImpl;
import rmi.HelloRemote;

public class RMIClient {
	public static void main(String args[]) throws MalformedURLException, RemoteException, NotBoundException {
		// System.setSecurityManager(new RMISecurityManager());//
		// 如果服务器和客户端不再同一台机器要加这行
//new HelloImpl();
		HelloRemote hello = (HelloRemote) Naming.lookup("rmi://127.0.0.1:6600/RMI_Hello");
		StringBuffer sb = new StringBuffer();
		System.out.println(sb.hashCode());
		String ret = hello.sayHello(sb);
		System.out.println(sb.hashCode());

		System.out.println("返回：" + ret);
		System.out.println("参数变化：" + sb);

		System.out.println("call remote method  ok！");
	}
}