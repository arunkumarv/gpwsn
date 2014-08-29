package in.gpwsn.server;

import in.gpwsn.nodes.Actuator;
import in.gpwsn.nodes.Entity;
import in.gpwsn.nodes.Location;
import in.gpwsn.nodes.Sensor;

import java.util.Enumeration;
import java.util.Hashtable;

public class LocationManager {

	
	
	private Hashtable<String, Location> locations = null;
	
	
	
	public LocationManager(Hashtable<String, Location> locations) {
	
		this.locations = locations;
	}

	
	
	public String getLocationName ( Entity entity ) {

		Enumeration <String> locationNames = locations.keys ();
		
		while ( locationNames.hasMoreElements() ) {
			
			String locationName = locationNames.nextElement ();
			
			Location location = locations.get ( locationName );
			
			System.out.println ( location.getName () );
			
			 
			Hashtable<String, Sensor> loc_sensors = location.getSensors();
			
			Enumeration<String> loc_sensor_ids = loc_sensors.keys();
			
			while ( loc_sensor_ids.hasMoreElements() ) {
			 
				String loc_sensor_id = loc_sensor_ids.nextElement();
				
				if ( loc_sensor_id.equals( entity.getSensorID() ) ) {
					
					return location.getName();
				}
			}
		}
		return null;
	}
}
