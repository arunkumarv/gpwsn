package in.gpwsn.nodes;

import java.io.Serializable;

public class Attribute implements Serializable {

	
	
	private String type = null;
	
	private String unit = null;
	
	private String output = null;
	
	
	
	public Attribute() {
		
	}
	
	
	
	
	public Attribute( String type, String unit, String output ) {
		
		this.type = type;
		
		this.unit = unit;
		
		this.output = output;
	}
	
	
	
	public String getOutput() {
		
		return output;
	}
	
	public String getType() {
		
		return type;
	}
	
}
