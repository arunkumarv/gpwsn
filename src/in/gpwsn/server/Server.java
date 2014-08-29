package in.gpwsn.server;

import in.gpwsn.contextxml.ContextDataParser;
import in.gpwsn.libs.ConnectionListener;
import in.gpwsn.libs.ContextEvent;
import in.gpwsn.libs.RemoteServices;
import in.gpwsn.nodes.Actuator;
import in.gpwsn.nodes.Entity;
import in.gpwsn.nodes.Location;
import in.gpwsn.nodes.Rule;
import in.gpwsn.nodes.Sensor;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Hashtable;

public class Server implements RemoteServices {

	
	
	private Hashtable<String, ConnectionListener> connectionListeners;
	
	private ContextDataParser cdp = null;
	
	
	private Hashtable<String, Sensor> sensors = null;
	
	private Hashtable<String, Actuator> actuators = null;
	
	private Hashtable<String, Location> locations = null;
	
	private Hashtable<String, Rule> lowLevelRules = null;
	
	private LocationManager locationmanager = null;
	
	private RuleManager rulemanager = null;
	
	private EntityManager entitymanager = null;
	
	public Server() {
		
		
		cdp = new ContextDataParser("context.xml");
		
		
		sensors = cdp.getAllSensors();
		
		actuators = cdp.getAllActuators();
		
		locations = cdp.getLocations();
		
		lowLevelRules = cdp.getLowLevelRules();
		
		connectionListeners = new Hashtable<String, ConnectionListener>(100);
		
		
		locationmanager = new LocationManager( locations );
	
		rulemanager = new RuleManager ( lowLevelRules, sensors );
		
		entitymanager = new EntityManager();
	}




	@Override
	public void publish ( String xmldata )  {
		
		Entity entity = new Entity ( xmldata );
		
		if ( sensors.containsKey ( entity.getSensorName() )  ) {
			
			System.out.println( "yoyo " + (entity.getSensorID()) );
			
			if ( connectionListeners.containsKey( entity.getSensorID()) ) {
				
				if ( entitymanager.isEntityChange (entity) ) {
				
					ContextEvent contextevent = new ContextEvent();
					
					contextevent.addEntity ( entity );
					
					contextevent.addLocationName ( locationmanager.getLocationName ( entity ) );
					
					contextevent.addRuleNameAndActions( rulemanager.getRuleNameAndActions( entity) );
					
					System.out.println(contextevent.getRuleNameAndAction().size());	
						
					try {
						
						connectionListeners.get( entity.getSensorID () ).remoteChanged( contextevent );
						
					} catch ( RemoteException e ) {
						
						e.printStackTrace();
						
						connectionListeners.remove( entity.getSensorName() );
					}
				}
				
				
			}
			
			else {
			
				System.out.println("no subscriber");
			}
			
		} else {
		
			System.out.println("invalid xml");
		}
	}

	
	
	



	@Override
	public void subscribe ( ConnectionListener connectionlistener, String sensorID ) throws RemoteException {
		
		connectionListeners.put( sensorID, connectionlistener );
		
		if (connectionListeners.containsKey(sensorID)) {
			
			System.out.println("yoyo");
		}
	}
	
	
	
	
	
}
