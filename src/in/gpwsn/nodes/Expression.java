package in.gpwsn.nodes;

import java.io.Serializable;

public class Expression implements Serializable{

	
	
	
	private String attribute;
	
	private String opr;
		
	private String sensor;
		
	private String value;
	
	
	public Expression ( String attribute, String opr, String sensor, String value ) {
		
		this.attribute = attribute;
		
		this.opr = opr;
		
		this.sensor = sensor;
		
		this.value = value;
	}
	
	
	public String getAttribute() {
		
		return attribute;
	}
	
	
	public String getOpr() {
		
		return opr;
	}
	
	public String getSensorName() {
		
		return sensor;
	}
	
	public String getValue() {
		
		return value;
	}
	
}
