package in.gpwsn.libs;

import java.io.Serializable;
import java.util.Hashtable;

import in.gpwsn.nodes.Action;
import in.gpwsn.nodes.Entity;

public class ContextEvent implements Serializable {

	
	
	
	private Entity entity = null;
	
	private String locationName = null;
	
	private Hashtable<String, Action> ruleNameAndActions = null;
	
	
	
	
	
	public ContextEvent() {
		
		
	}
	
	
	
	
	public void addLocationName( String locationName ) {
		
		this.locationName = locationName;
	}

	public void addEntity ( Entity entity ) {
		
		this.entity = entity;
	}

	public void addRuleNameAndActions( Hashtable<String, Action> ruleNameAndActions ) {
			
		this.ruleNameAndActions = ruleNameAndActions;
	}
	
	public Hashtable<String, Action> getRuleNameAndAction () {
		
		return ruleNameAndActions;
	}
	
	public Entity getEntity() {
		
		return entity;
	}
}
