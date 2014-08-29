package in.gpwsn.nodes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Actuator implements Serializable{

	
	private String name = null;
	
	private List<String> commands;
	
	
	public Actuator() {
		
		commands = new ArrayList<String>();
	}
	
	public void setName ( String name ) {
		
		this.name = name;
	}
	
	public void addCommand ( String command ) {
	
		commands.add ( command );
	}

	public List<String> getCommands() {
		
		return commands;
	}
	
	public String getName () {
		
		return name;
	}
	
	
}
