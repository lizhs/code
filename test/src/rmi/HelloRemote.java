package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloRemote extends Remote {
	public String sayHello(StringBuffer sb) throws RemoteException;
}