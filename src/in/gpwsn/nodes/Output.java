package in.gpwsn.nodes;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Output implements Serializable {

	
	private String name = null;
	
	private String unit = null;
	
	private String value = null;
	
	
	public Output() {
		
	}
	
	
	public void setName ( String name ) {
		
		this.name = name;
	}
	
	
	public void setUnit ( String unit ) {
		
		this.unit = unit;
	}
	
	public void setValue ( String value ) {
		
		this.value = value;
	}
	
	public String getName() {
		
		return name;
	}
	
	public String getValue() {
		
		return value;
	}
	
	public String getUnit() {
		
		return unit;
	}
}
