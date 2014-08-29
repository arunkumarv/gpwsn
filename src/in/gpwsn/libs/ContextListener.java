package in.gpwsn.libs;

import in.gpwsn.nodes.Entity;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ContextListener extends Remote {

	void contextChanged( ContextEvent ce ) throws RemoteException;
}
