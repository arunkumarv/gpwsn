package in.gpwsn.libs;


import in.gpwsn.nodes.Entity;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Connection implements ConnectionListener {
	
	
	
	
	
	private Registry serverregistry = null;
	
	private ConnectionListener connectionlistener = null;
	
	private RemoteServices remoteservices = null;
	
	private ContextListener contextlistener = null;
	
	
	
	
	
	public Connection( int port ) throws RemoteException, NotBoundException {
		
		serverregistry = LocateRegistry.getRegistry( "localhost",port );
		
		connectionlistener = (ConnectionListener) UnicastRemoteObject.exportObject(this, 0);
		
		remoteservices = (RemoteServices) serverregistry.lookup("server");
	}

	
	
	
	public void close() throws NoSuchObjectException {
		
		UnicastRemoteObject.unexportObject( this, true );
	}
	
	
	@Override
	public void remoteChanged( ContextEvent ce ) throws RemoteException {
		
		if ( contextlistener != null ) {
			
			contextlistener.contextChanged( ce );
		}
	}
	
	
	
	
	
	
	public void subscribe ( ContextListener contextlistener, String sensorID ) throws RemoteException {
		
		this.contextlistener = contextlistener;
		
		remoteservices.subscribe ( connectionlistener, sensorID );
	}
	
	public void publish( String xmldata ) throws RemoteException {
		
		remoteservices.publish(xmldata);
	}
	
	
	
	
	
	
	
}
