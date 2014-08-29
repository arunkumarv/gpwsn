package in.gpwsn.libs;

import in.gpwsn.nodes.Entity;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConnectionListener extends Remote {

	void remoteChanged( ContextEvent ce ) throws RemoteException;
}
