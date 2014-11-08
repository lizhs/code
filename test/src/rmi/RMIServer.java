package rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


/**
 * http://blog.csdn.net/leon709/article/details/7090436
 */
public class RMIServer {
	public static void main(String[] args) throws RemoteException, MalformedURLException {

		HelloRemote hello = new HelloImpl();
		System.out.println(hello);
//		Naming.rebind("hello", hello);
		try {
			LocateRegistry.createRegistry(6600);  
            //注册通讯路径  
            Naming.rebind("rmi://127.0.0.1:6600/RMI_Hello", hello);  
            System.out.println("Service Start!");  
            
//			Naming.bind("rmi://127.0.0.1:1099/RMI_Hello",hello);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}