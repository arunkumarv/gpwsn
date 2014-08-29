package in.gpwsn.nodes;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class Sensor implements Serializable {

	
	private String name = null;
	
	private String related_to = null;

	private Hashtable<String, Attribute> attributes = null;
	
	
	
	public Sensor() {
		
		attributes = new Hashtable<String, Attribute>(50);
	}
	
	
	public void setName ( String name ) {
		
		this.name = name;
	}
	
	
	public void setRelatedto( String related_to ) {
		
		this.related_to = related_to;
	}

	
	public String getName() {
		
		return name;
	}


	public void addAttribute( Attribute attribute ) {
		
		attributes.put(attribute.getOutput(), attribute );
	}


	public Hashtable<String, Attribute> getAttributes() {
		
		return attributes;
	}


	public Attribute getAttribute( String attribute ) {
	
		Enumeration<String> attribNames = attributes.keys();
		
		while ( attribNames.hasMoreElements() ) {
			
			String attribName = attribNames.nextElement();
			
			if ( attribName.equals(attribute)) {
				
				return attributes.get(attribName);
			}
		}
		
		return null;
	}

}
