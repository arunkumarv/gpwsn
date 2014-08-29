package in.gpwsn.libs;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteServices extends Remote {

	void publish( String xmldata ) throws RemoteException;
	
	void subscribe( ConnectionListener rl, String xmldata ) throws RemoteException;
}
