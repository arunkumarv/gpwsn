package in.gpwsn.client;

import java.rmi.NoSuchObjectException;

import in.gpwsn.libs.Connection;


public class ClientPublish implements Runnable {

	
	
	
	
	private Connection conn = null;
	
	private String xmldata = null;
	
	private Thread thread = null;
	
	
	
	public ClientPublish( String xmldata ) {
	
		this.xmldata = xmldata;
		
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
			
			conn.publish( xmldata );
		
		} catch ( Exception e ) {
			
			e.printStackTrace();
		}
		

		try {
		
			Thread.sleep(10000);
		
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
	
		
		try {
		
			conn.close();
		
		} catch (NoSuchObjectException e) {
			
			e.printStackTrace();
		}

	}
	
	
	
	public static void main ( String[] args ) {
		
		String xmldata = "<loadcell><sensor id='loadcell1'/><weight>151</weight></loadcell>";
		
		new ClientPublish( xmldata );
	}
	
}
