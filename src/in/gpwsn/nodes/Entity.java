package in.gpwsn.nodes;



import java.io.Serializable;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


public class Entity implements Serializable {
	
	
	
	private String xmldata = null;
	
	
	
	
	private String name = null;
	
	private String sensorID = null;
	
	private String personID = null;
	
	private Output output = null;
	
	
	private int elementPosition = 0;
	
	private Hashtable<String, Output> outputs;
	
	
	
	
	
	
	public Entity() {
	
		
	}
	
	
	
	

	public Entity( String xmldata ) {
		
		this.xmldata = xmldata;
		
		outputs = new Hashtable<String, Output>(50);
		
		process();
	}
	
	
	
	
	
	public void process() {
		
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		
		StringReader sr = new StringReader(xmldata);
		
		XMLEventReader eventReader = null;
		
		try {
			
			eventReader = inputFactory.createXMLEventReader(sr);
		
		} catch (XMLStreamException e) {
			
			e.printStackTrace();
		}
		
		elementPosition = 1;
		
		while ( eventReader.hasNext() ) {
			
			
			XMLEvent event = null;
			
			
			try {
				
				event = eventReader.nextEvent();
			
			} catch (XMLStreamException e) {
				
				e.printStackTrace();
			}
			
			
			
			
			
			if ( event.isStartElement() ) {
				
				StartElement startElement = event.asStartElement();	
				
				
				
				if ( name == null && elementPosition == 1 ) {
					
					name = startElement.getName().getLocalPart();
					
					System.out.println( name );
					
					elementPosition = 2;
				} 
				
				
				
				else if (elementPosition == 2 && sensorID == null && startElement.getName().getLocalPart().equalsIgnoreCase("SENSOR")) {
					
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attributes = startElement.getAttributes();
					
					Attribute attribute = attributes.next();
					
					if ( attribute.getName().toString().equalsIgnoreCase("ID")) {
						
						sensorID = attribute.getValue();
						
						System.out.println(sensorID);
					}
					
					elementPosition = 3;
					
				}
				
				
				else if ( elementPosition == 3 && personID == null && startElement.getName().getLocalPart().equalsIgnoreCase("PERSON")) {
					
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attributes = startElement.getAttributes();
					
					Attribute attribute = attributes.next();
					
					if ( attribute.getName().toString().equalsIgnoreCase("ID")) {
						
						sensorID = attribute.getValue();
						
						System.out.println(sensorID);
					}
					
					elementPosition = 4;
					
				}
				
				
				else if ( sensorID != null && name != null && elementPosition > 2 ) {
					
					System.out.println(startElement.getName().getLocalPart());
					
					output = new Output();
					
					output.setName( startElement.getName().getLocalPart() );
					
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attributes = startElement.getAttributes();
					
					if ( attributes.hasNext() ) {
						
						Attribute attribute = attributes.next();
						
						if ( attribute.getName().toString().equalsIgnoreCase("UNIT")) {
							
							output.setUnit(attribute.getValue());
						}
					}
					
					try {
				
						event = eventReader.nextEvent();
					
					} catch (XMLStreamException e) {
						
						e.printStackTrace();
					}
					
					System.out.println( event.asCharacters().getData() );
					
					output.setValue( event.asCharacters().getData() );
				}
				
				
				
			}
			
			
			
			
			
			if ( event.isEndElement() ) {
				
				EndElement endElement = event.asEndElement();
				
				if ( output != null ) {
					
					if ( endElement.getName().getLocalPart().equalsIgnoreCase(output.getName())) {
						
						outputs.put( output.getName(), output );
					}
					
					output = null;
				}
			}
			
			
			
			
			
		}
	}
	
	
	
	
	
	
	public String getSensorName() {
		
		return name;
	}
	
	public String getSensorID() {
		
		return sensorID;
	}
	
	public String getPersonID() {
		
		return personID;
	}
	
	public Hashtable<String, Output> getOutputs() {
		
		return outputs;
	}
}
