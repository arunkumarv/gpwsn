package in.gpwsn.contextxml;

import in.gpwsn.nodes.Location;
import in.gpwsn.nodes.Sensor;

import java.util.Enumeration;
import java.util.Hashtable;

public class ContextXMLTestRead {

	
	public static void main ( String[] args ) {
		
		ContextDataParser cdp = new ContextDataParser("context.xml");
		
		Hashtable<String, Location> locations = cdp.getLocations();
		
		Enumeration<String> locationNames = locations.keys();
		
		while ( locationNames.hasMoreElements() ) {
			
			String locationName = locationNames.nextElement();
			
			Location location = locations.get(locationName);
			
			System.out.println( "location : " + location.getName() );
			
			Hashtable<String, Sensor> sensors = location.getSensors();
			
			Enumeration<String> sensorIDs = sensors.keys();
			
			while ( sensorIDs.hasMoreElements() ) {
				
				String sensorID = sensorIDs.nextElement();
				
				Sensor sensor = sensors.get( sensorID );
				
				System.out.println( " |_ " + sensorID + ":" + sensor.getName() );
			}
		}
	}
}
