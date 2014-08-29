package in.gpwsn.contextxml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
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

import in.gpwsn.nodes.Action;
import in.gpwsn.nodes.Actuator;
import in.gpwsn.nodes.Expression;
import in.gpwsn.nodes.Location;
import in.gpwsn.nodes.Rule;
import in.gpwsn.nodes.Sensor;

public class ContextDataParser {

	
	
	
	private Hashtable<String, Sensor> sensors = null;
	
	private Hashtable<String, Location> locations = null;
	
	private Hashtable<String, Actuator> actuators = null;
	
	private Hashtable<String, Rule> lowLevelRules = null;
	
	private XMLInputFactory inputFactory = null;
	
	private InputStream in = null;
	
	private XMLEventReader eventReader = null;
	
	private XMLEvent event = null;
	


	
	public ContextDataParser ( String contextxmlfile ) {
		
		inputFactory = XMLInputFactory.newInstance();
		
		try {
		
			in = new FileInputStream( contextxmlfile );
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
			System.exit(1);
		}
		
		try {
			
			eventReader = inputFactory.createXMLEventReader(in);
		
		} catch (XMLStreamException e) {
			
			e.printStackTrace();
		}
		
		sensors = new Hashtable <String, Sensor> (10);
		
		actuators =  new Hashtable <String, Actuator> (10);
		
		locations = new Hashtable<String, Location>(10);
		
		lowLevelRules = new Hashtable<String, Rule>(10);
		
		while ( eventReader.hasNext() ) {			
			
			
			try {
				
				event = eventReader.nextEvent();

				if (event.isStartElement() ) {
					
					StartElement startElement = event.asStartElement ();
					
					if ( startElement.getName().getLocalPart().equals ( "SENSOR_DATA" ) ) {
						
						parseSensorData();
						
						continue;
					}
					
					if ( startElement.getName().getLocalPart().equals("ACTUATOR_DATA") ) {
					
						parseActuatorData();
						
						continue;
					}
					
					if ( startElement.getName().getLocalPart().equals("LOCATION_DATA") ) {
						
						parseLocationData();
						
						continue;
					}
					
					if ( startElement.getName().getLocalPart().equals("LOW_LEVEL_RULES") ) {
						
						parseLowLevelRules();
						
						continue;
					}
					
				}
				
			} catch (XMLStreamException e) {
			
				e.printStackTrace();
			}
			
		}
		
		try {
			
			eventReader.close();
			
		} catch (XMLStreamException e1) {
			
			e1.printStackTrace();
		}
		
		
		try {
		
			in.close();
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	

	public void parseLowLevelRules() {
		
		Rule rule = null;
		
		while ( eventReader.hasNext() ) {
			
			try {
				
				event = eventReader.nextEvent();
			
			} catch (XMLStreamException e) {
			
				e.printStackTrace();
			}
			
			if ( event.isStartElement() ) {
				
				if ( event.asStartElement().getName().getLocalPart().equals( "RULE") ) {
					
					rule = new Rule();
					
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attributes = event.asStartElement().getAttributes();
					
					while ( attributes.hasNext() ) {
						
						Attribute attribute = attributes.next();
						
						if ( attribute.getName().toString().equals("NAME") ) {
							
							rule.setName( attribute.getValue());
						}
					}
					
				}
				
				if ( event.asStartElement().getName().getLocalPart().equals("DESCRIPTION")) {
					
					try {
						
						event = eventReader.nextEvent();
					
					} catch ( XMLStreamException e ) {
						
						e.printStackTrace();
					}
					
					if ( event.isCharacters() ) {
						
						rule.addDescription( event.asCharacters().getData() );
						
						try {
							
							event = eventReader.nextEvent();
						
						} catch ( XMLStreamException e ) {
							
							e.printStackTrace();
						}
						
					}
					
					try {
						
						event = eventReader.nextEvent();
					
					} catch ( XMLStreamException e ) {
						
						e.printStackTrace();
					}
					
				}
				if ( event.isStartElement() ) {
					
					if ( event.asStartElement().getName().getLocalPart().equals("EXPRESSION")) {
						
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = event.asStartElement().getAttributes();
						
						String attrib = null, opr = null, sensor = null, value = null;
						
						while ( attributes.hasNext() ) {
							
							Attribute attribute = attributes.next();
							
							if ( attribute.getName().toString().equals("ATTRIBUTE") ) {
								
								attrib = attribute.getValue();
							}
							
							if ( attribute.getName().toString().equals("OPR") ) {
								
								opr = attribute.getValue();
							}
	
							if ( attribute.getName().toString().equals("SENSOR") ) {
								
								sensor = attribute.getValue();
							}
							
							if ( attribute.getName().toString().equals("VALUE") ) {
								
								value = attribute.getValue();
							}
						}
						
						rule.addExpression( new Expression (attrib, opr, sensor, value ) );
					}
				}
				
				
				if ( event.isStartElement()) {
			
					if ( event.asStartElement().getName().getLocalPart().equals("ACTION")) {
					
						Action action = new Action();
						
	
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = event.asStartElement().getAttributes();
						
						while ( attributes.hasNext() ) {
							
							Attribute attribute = attributes.next();
							
							if ( attribute.getName().toString().equals("ACTUATOR") ) {
								
								action.setActuator(attribute.getValue());
							}
							if ( attribute.getName().toString().equals("COMMAND") ) {
								
								action.setCommand ( attribute.getValue() );
							}
							
							rule.setAction(action);
						}
					}
				}
			}
			
			
			if ( event.isEndElement() ) {
				
				if ( event.asEndElement().getName().getLocalPart().equals("RULE")) {
					
					lowLevelRules.put( rule.getName(), rule );
				}
				
				if ( event.asEndElement().getName().getLocalPart().equals("LOW_LEVEL_RULES")) {
					
					return;
				}
			}
		}
	}
	
	
	
	
	public void parseLocationData () {
	
		Location location = null;
		
		while ( eventReader.hasNext() ) {
			
			try {
				
				event = eventReader.nextEvent();
			
			} catch (XMLStreamException e) {
			
				e.printStackTrace();
			}
			
			if ( event.isStartElement() ) {
				
				
				if ( event.asStartElement().getName().getLocalPart().equals("LOCATION")) {
					
					location = new Location();
					
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attributes = event.asStartElement().getAttributes();
					
					while ( attributes.hasNext() ) {
						
						Attribute attribute = attributes.next();
						
						if ( attribute.getName().toString().equals("NAME") ) {
							
							location.setName( attribute.getValue());
						}
					}
				}
				
				if (event.asStartElement().getName().getLocalPart() == "SENSOR" ) {
					
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attributes = event.asStartElement().getAttributes();
					
					String sid = null; Sensor type = null;
					
					while ( attributes.hasNext() ) {
						
						Attribute attribute = attributes.next();
						
						if ( attribute.getName().toString().equals("S_ID")) {
							
							sid = attribute.getValue();
						}
						
						if ( attribute.getName().toString().equals("TYPE")) {
							
							type = ( Sensor ) sensors.get( attribute.getValue());
						}

					}
					
					location.addSensor( sid, type );
				}

				if (event.asStartElement().getName().getLocalPart() == "ACTUATOR" ) {
					
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attributes = event.asStartElement().getAttributes();
					
					String aid = null; Actuator type = null;
					
					while ( attributes.hasNext() ) {
						
						Attribute attribute = attributes.next();
						
						if ( attribute.getName().toString().equals("A_ID") ) {
							
							aid = attribute.getValue();
						}
						
						if ( attribute.getName().toString().equals("TYPE") ) {
							
							type = ( Actuator ) actuators.get ( attribute.getValue() );
						}

					}
					
					location.addActuator ( aid, type );
				}

				
			}
			
			if ( event.isEndElement() ) {
				
				if ( event.asEndElement().getName().getLocalPart().equals ( "SENSOR" ) ) {
					
				}

				if ( event.asEndElement().getName().getLocalPart().equals ( "ACTUATOR" ) ) {
					
				}
				
				if ( event.asEndElement().getName().getLocalPart().equals ( "LOCATION" ) ) {
					
					locations.put( location.getName(), location );
				}
				
				if ( event.asEndElement().getName().getLocalPart().equals ("LOCATION_DATA") ) {
					
					return;
				}
			}
			
			
		}
	}
	
	
	
	
	public void parseSensorData () {
		
		Sensor sensor = null;
		
		while ( eventReader.hasNext() ) {
			
			try {
				
				event = eventReader.nextEvent();
				
				if ( event.isStartElement() ) {
					
					StartElement startElement = event.asStartElement();
					
					if ( startElement.getName().getLocalPart().equals("SENSOR")) {
						
						sensor = new Sensor();
						
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement.getAttributes();
						
						while ( attributes.hasNext() ) {
							
							Attribute attribute = attributes.next();
							
							if ( attribute.getName().toString().equals("NAME")) {
								
								sensor.setName(attribute.getValue());
							}
							
							if ( attribute.getName().toString().equals("RELATED_TO")) {
								
								sensor.setRelatedto(attribute.getValue());
							}
						}
					}
					
					if ( startElement.getName().getLocalPart() == "ATTRIBUTE" ) {
						
						
						@SuppressWarnings("unchecked")
						Iterator<Attribute> attributes = startElement.getAttributes();
						
						String type = null, unit = null, output = null;
						
						while ( attributes.hasNext() ) {
							
							Attribute attribute = attributes.next();
							
							if ( attribute.getName().toString().equals("DATATYPE")) {
								
								type = attribute.getValue();
							}
							
							if ( attribute.getName().toString().equals("DATAUNIT")) {
								
								unit = attribute.getValue();
							}
							
							if ( attribute.getName().toString().equals("OUTPUT")) {
								
								output = attribute.getValue();
							}
						}
							
						sensor.addAttribute( new in.gpwsn.nodes.Attribute(type, unit, output) );
					}


					
				}
				
				if ( event.isEndElement() ) {
					
					if ( event.asEndElement().getName().getLocalPart().equals("SENSOR")) {
						
						sensors.put( sensor.getName(), sensor );
					} 
					
					if ( event.asEndElement().getName().getLocalPart().equals("SENSOR_DATA") ) {
						
						return;
					}
				}
				
			
			} catch( XMLStreamException e ) {
				
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	public void parseActuatorData() {
		
		Actuator actuator = null;

		while ( eventReader.hasNext() ) {
			
			try {
			
				event = eventReader.nextEvent();
			
			} catch( XMLStreamException e ) {
			
				e.printStackTrace();
			}
			
			
			if ( event.isStartElement() ) {
				
				
				StartElement startElement = event.asStartElement();
				
				
				if ( startElement.getName().getLocalPart().equals("ACTUATOR")) {
					
					actuator = new Actuator();
					
					@SuppressWarnings("unchecked")
					Iterator<Attribute> attributes = startElement.getAttributes();
					
					while ( attributes.hasNext() ) {
						
						Attribute attribute = attributes.next();
						
						if ( attribute.getName().toString().equals("NAME")) {
							
							actuator.setName(attribute.getValue());
						}
					}
				}
				
				if ( startElement.getName().getLocalPart().equals("COMMAND") ) {
					
					try {
					
						event = eventReader.nextEvent();
					
					} catch (XMLStreamException e) {
						
						e.printStackTrace();
					}
					
					if ( event.isCharacters() ) {
						
						actuator.addCommand( event.asCharacters().getData() );
					}
				}
				
				
				
			}
			
			
			if ( event.isEndElement() ) {
				
				EndElement endElement = event.asEndElement();
				
				if ( endElement.getName().getLocalPart().equals("COMMAND")) {
					
				}
				
				if ( endElement.getName().getLocalPart().equals("ACTUATOR")) {
					
					actuators.put( actuator.getName(), actuator );
				}
				
				if ( endElement.getName().getLocalPart().equals("ACTUATOR_DATA")) {
					
					return;
				}
			}
		}
	}

	
	
	
	
	public Hashtable<String, Sensor> getAllSensors () {
	
		return sensors;
	}
	
	
	

	public Hashtable<String, Actuator> getAllActuators () {
		
		return actuators;
	}
	
	
	
	
	public Hashtable<String, Location> getLocations() {
		
		return locations;
	}
	
	
	public Hashtable<String, Rule> getLowLevelRules() {
		
		return lowLevelRules;
	}
	
	
	public static void main ( String[] args ) {
		
		
		
		ContextDataParser cdpt = new ContextDataParser("context.xml");
		
		
		//---------------------All Sensors------------------
		
//		Hashtable<String, Sensor> sensors = cdpt.getAllSensors();
//		
//		Enumeration<String> sensorNames = sensors.keys();
//		
//		while ( sensorNames.hasMoreElements() ) {
//			
//			String sensorName = sensorNames.nextElement();
//			
//			Sensor sensor = sensors.get(sensorName);
//			
//			System.out.println(sensor.getName());
//		}
//		
		
		
		//-----------------All Actuators--------------------
		
//		Hashtable<String, Actuator> actuators = cdpt.getAllActuators();
//		
//		Enumeration<String> actuatorNames = actuators.keys();
//		
//		while ( actuatorNames.hasMoreElements() ) {
//			
//			String actuatorName = actuatorNames.nextElement();
//			
//			Actuator actuator = actuators.get(actuatorName);
//			
//			System.out.println(actuator.getName());
//			
//			for ( String command : actuator.getCommands()) {
//				
//				System.out.println( "--" + command );
//			}
//		}
		
		//-----------------All Locations--------------------
		
		Hashtable <String, Location> locations = cdpt.getLocations ();
		
		Enumeration <String> locationNames = locations.keys ();
		
		while ( locationNames.hasMoreElements() ) {
			
			String locationName = locationNames.nextElement ();
			
			Location location = locations.get ( locationName );
			
			System.out.println ( location.getName () );
			
			Hashtable<String, Actuator> loc_actuators = location.getActuators();
			 
			Enumeration<String> loc_act_names = loc_actuators.keys();
			 
			while ( loc_act_names.hasMoreElements() ) {
				 
				String loc_act_name = loc_act_names.nextElement();
				 
				Actuator loc_actuator = loc_actuators.get(loc_act_name);
				 
				System.out.println( "  -Actuator-" + loc_actuator.getName() );
			}
			 
			 
			Hashtable<String, Sensor> loc_sensors = location.getSensors();
			
			Enumeration<String> loc_sensor_names = loc_sensors.keys();
			
			while ( loc_sensor_names.hasMoreElements() ) {
			 
				String loc_sensor_name = loc_sensor_names.nextElement();
				
				Sensor loc_sensor = loc_sensors.get(loc_sensor_name);
					 
				System.out.println( "  -Sensor-" + loc_sensor.getName() );
			}
		}
		
		//---------------------Get Low Level Rules----------------------
		
		Hashtable<String, Rule> lowLevelRules = cdpt.getLowLevelRules();
		
		Enumeration<String> llrulesnames = lowLevelRules.keys();
		
		while ( llrulesnames.hasMoreElements() ) {
			
			String llrulename = llrulesnames.nextElement();
			
			Rule lowLevelRule = lowLevelRules.get(llrulename);
			
			System.out.println( lowLevelRule.getName() );
			
			List<Expression> expList = lowLevelRule.getExpressionList();
			
			for ( Expression expression : expList ) {
				
				System.out.println( "  -Attribute- " + expression.getAttribute () );
				
				System.out.println( "  -Opr- " + expression.getOpr () );
				
				System.out.println( "  -Sensor- " + expression.getSensorName() );
				
				System.out.println( "  -Value- " + expression.getValue () +"\n" );
			}
			
			Action action = lowLevelRule.getAction();
			
			System.out.println( action.getActuator() + " "  + action.getCommand() );
		}
		
	}


}
