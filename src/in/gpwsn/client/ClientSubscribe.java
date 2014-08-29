package in.gpwsn.client;

import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;

import in.gpwsn.libs.Connection;
import in.gpwsn.libs.ContextEvent;
import in.gpwsn.libs.ContextListener;
import in.gpwsn.libs.RemoteServices;
import in.gpwsn.nodes.Action;
import in.gpwsn.nodes.Entity;


public class ClientSubscribe implements ContextListener, Runnable {

	
	
	
	
	private Connection conn = null;
	
	private String msg = null;
	
	private Thread thread;
	
	public ClientSubscribe( String msg ) {
	
		this.msg = msg;
		
		thread = new Thread(this);
		
		thread.start();
	}

	@Override
	public void run() {
		

		try {
		
			conn = new Connection(1099);
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		try {
			
			conn.subscribe( this, "loadcell1" );
		
		} catch ( Exception e ) {
			
			e.printStackTrace();
		}
		
		try {
		
			Thread.sleep(20000);
		
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
		
		try {
		
			conn.close();
		
		} catch (NoSuchObjectException e) {
			
			e.printStackTrace();
		}

	}
	
	
	
	
	@Override
	public void contextChanged ( ContextEvent ce ) {
		
		System.out.println(ce.getEntity().getSensorID());
		
		Hashtable<String, Action> actions = ce.getRuleNameAndAction();
		System.out.println( actions.size());
		Enumeration<String> actionNames = actions.keys();
		
		while ( actionNames.hasMoreElements() ) {
			
			String actionName = actionNames.nextElement();
			
			Action action = actions.get(actionName);
			
			System.out.println( action.getActuator() );
		}
	}

	
	
	
	public static void main ( String[] args ) {
		
		new ClientSubscribe("arun");
	}
	
}
