package in.gpwsn.nodes;


import java.io.Serializable;
import java.util.Hashtable;


public class Location implements Serializable{

	
	private String name;
	
	private Hashtable<String, Sensor> sensors;
	
	private Hashtable<String, Actuator> actuators;
	
	public Location() {
		
		sensors = new Hashtable<String, Sensor>(10);
		
		actuators = new Hashtable<String, Actuator>(10);
	}
	
	
	public void setName ( String name ) {
		
		this.name = name;
	}

	public String getName() {
		
		return name;
	}
	
	public void addSensor ( String sid, Sensor sensor ){
		
		sensors.put( sid, sensor );
	}
	
	public void addActuator ( String aid, Actuator actuator ){
		
		actuators.put( aid, actuator );
	}

	public Hashtable<String, Sensor> getSensors() {
		
		return sensors;
	}
	
	public Hashtable<String, Actuator> getActuators() {
		
		return actuators;
	}
}
