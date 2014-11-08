package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements HelloRemote {
	public HelloImpl() throws RemoteException {
		super();
	}

	public String sayHello(StringBuffer sb) throws RemoteException {
		
		System.out.println(this);
		sb.append("xxx");
		System.out.println("Hello World!"+sb.hashCode());
		return sb.toString();
	}
}