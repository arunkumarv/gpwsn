package in.gpwsn.temp;

import java.io.StringReader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class xmltemp {

	
	public static void main ( String[] args ) {
		
		String xmldata = "<loadcell> <sensor id='loadcellid1'/> <weight>156</weight>  </loadcell>";
		
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		
		StringReader sr = new StringReader(xmldata);
		
		XMLEventReader eventReader = null;
		
		try {
			
			eventReader = inputFactory.createXMLEventReader(sr);
		
		} catch (XMLStreamException e) {
			
			e.printStackTrace();
		}
		
		while ( eventReader.hasNext() ) {
			
			XMLEvent event = null;
			
			try {
			
				event = eventReader.nextEvent();
			
			} catch (XMLStreamException e) {
				
				e.printStackTrace();
			}
			
			if (event.isStartElement() ) {
				
				StartElement se = event.asStartElement();
				
				System.out.println(se.getName());
				
				if (event.asStartElement().getName().getLocalPart().equals("weight")) {
					
					try {
					
						event = eventReader.nextEvent();
					
					} catch (XMLStreamException e) {
						
						e.printStackTrace();
					}
					
					System.out.println(event.asCharacters().getData());
				}
			}
			
			
			if ( event.isEndElement() ) {
				
				EndElement ee = event.asEndElement();
				
				System.out.println(ee.getName());
			}
		}
		
	}
}
