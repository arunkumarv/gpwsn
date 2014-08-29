package in.gpwsn.server;

import in.gpwsn.nodes.Action;
import in.gpwsn.nodes.Attribute;
import in.gpwsn.nodes.Entity;
import in.gpwsn.nodes.Expression;
import in.gpwsn.nodes.Output;
import in.gpwsn.nodes.Rule;
import in.gpwsn.nodes.Sensor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class RuleManager {

	
	
	
	private Hashtable<String, Rule> lowLevelRules= null;
	
	private Hashtable<String, Sensor> sensors = null;
	
	private Hashtable<String, Action> actions = null;  //<rule_name, action>
	
	
	public RuleManager (Hashtable<String, Rule> lowLevelRules, Hashtable<String, Sensor> sensors ) {
	
		this.lowLevelRules = lowLevelRules;
		
		this.sensors = sensors;
		
		actions = new Hashtable<String, Action>();
	}
	
	
	public Hashtable<String, Action> getRuleNameAndActions ( Entity entity ) {
		
		Hashtable<String, Action> actions = new Hashtable<String, Action>();
		
		Hashtable<String, Output> outputs = entity.getOutputs();
		
		Enumeration<String> llrulesnames = lowLevelRules.keys();
		   
		while ( llrulesnames.hasMoreElements() ) {
			
			String llrulename = llrulesnames.nextElement();
			
			Rule lowLevelRule = lowLevelRules.get(llrulename);
			
			List<Expression> expList = lowLevelRule.getExpressionList();
			
			for ( Expression expression : expList ) {
				
				if ( expression.getSensorName().equals(entity.getSensorName())) {
					
					Sensor sensor = sensors.get(entity.getSensorName());
					
					Attribute attribute = sensor.getAttribute( expression.getAttribute() );
					
					if ( checkRule ( entity, expression, attribute ) ) {
						
						actions.put( lowLevelRule.getName(), lowLevelRule.getAction() );
					}
				}
					
			}
		}
		
		
		return actions;
	}


	private boolean checkRule( Entity entity, Expression expression,
			Attribute attribute ) {
		
		if ( attribute.getType().equals("double") ) {
			
			if ( expression.getOpr().equals(">") ) {
				
				Hashtable<String, Output> outputs = entity.getOutputs();
				
				Enumeration<String> outputNames = outputs.keys();
				
				while ( outputNames.hasMoreElements() ) {
					
					String outputName = outputNames.nextElement();
					
					Output output = outputs.get(outputName);
					
					System.out.println( output.getValue() +" " + expression.getValue());
					
					if (Double.parseDouble(output.getValue()) > Double.parseDouble(expression.getValue()) ) {
						
						return true;
					}
				}
			}
		}
		return false;
	}
}
