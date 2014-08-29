package in.gpwsn.nodes;

import java.io.Serializable;



public class Action implements Serializable {
	
	
	
	private String actuator;
	
	private String command;
	
	
	public Action() {
		
		
	}
	
	public void setActuator ( String actuator ) {
		
		this.actuator = actuator;
	}
	
	public void setCommand ( String command ) {
		
		this.command = command;
	}
	
	public String getActuator() {
		
		return actuator;
	}
	
	public String getCommand() {
		
		return command;
	}
}