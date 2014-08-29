package in.gpwsn.server;

import in.gpwsn.libs.RemoteServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main  {

	public Main() {
		
	}
	
	public static void main ( String[] args ) {
		
		Server server = new Server();
		
		Registry registry = null;
		
		RemoteServices rs = null;
		
		
		
		
		try {
	
			registry = LocateRegistry.createRegistry ( 1099 );
		
		} catch ( RemoteException e ) {
			
			e.printStackTrace();
		}
		
		
		
		
		try {
		
			rs = ( RemoteServices ) UnicastRemoteObject.exportObject ( server, 0 );
		
		} catch ( RemoteException e ) {
			
			e.printStackTrace();
		}
		
		
		
		
		
		
		try {
		
			registry.rebind ( "server", rs );
		
		} catch (AccessException e) {
			
			e.printStackTrace();
		
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}
		
		//--------------CMD LINE-------------------
		
		int i = 721;
		
		while ( true ) {
			
			System.out.println("Select:\n0 - Exit");
			
			BufferedReader reader = new BufferedReader ( new InputStreamReader ( System.in ) );
			
			try {
				
				i = Integer.valueOf ( reader.readLine() );
				
			} catch ( NumberFormatException e ) {
				
				System.out.println( e.getMessage() );
				
			} catch ( IOException e ) {
				
				e.printStackTrace();
			}
			
			if ( i == 0 ) {
				
				try {
					
					reader.close();
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
				break;
			}
			
		}
		
		//-----------------------------------------
		
		try {
			
			registry.unbind("server");
			
		} catch (AccessException e) {
			
			e.printStackTrace();
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
			
		} catch (NotBoundException e) {
			
			e.printStackTrace();
		}
		
		try {
		
			UnicastRemoteObject.unexportObject(server, true);
		
		} catch (NoSuchObjectException e) {
			
			e.printStackTrace();
		}
		System.exit(0);
	}
}
