package in.gpwsn.server;

import in.gpwsn.nodes.Entity;
import in.gpwsn.nodes.Output;

import java.util.Enumeration;
import java.util.Hashtable;

public class EntityManager {

	
	private Hashtable<String, Entity> entityList = null;
	
	
	public EntityManager () {
		 
		entityList = new Hashtable<String, Entity>(100);
		
	}
	
	public boolean isEntityChange ( Entity entity ) {
		
		if ( !entityList.containsKey( entity.getSensorName() ) ) {
			
			entityList.put(entity.getSensorName(), entity );
			
			return true;
		
		} else  {
		
			Entity oldEntity = entityList.get(entity.getSensorName());
			
			Hashtable<String, Output> oldOutputs = oldEntity.getOutputs();
			
			Hashtable<String, Output> newOutputs = entity.getOutputs();
			
			Enumeration<String> keys = oldOutputs.keys();
			
			while ( keys.hasMoreElements() ) {
				
				String key = keys.nextElement();
				
				Output oldOutput = oldOutputs.get(key);
				
				Output newOutput = newOutputs.get(key);
				
				if ( oldOutput.getValue() != newOutput.getValue() ) {
					
					return true;
				}
			}
			
			return false;
		}
		
	}
}
