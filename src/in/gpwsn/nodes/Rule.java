package in.gpwsn.nodes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rule implements Serializable {

	
	
	
	private String name;
	
	private String description;
	
	private List<Expression> expressionList;
	
	private Action action;
	
	
	
	
	public Rule() {
		
		expressionList = new ArrayList<Expression>();
		
		action = new Action();
	}
	
	
	
	
	
	public void addDescription ( String description ) {
		
		this.description = description;
	}


	public void addExpression( Expression expression ) {
		
		expressionList.add(expression);
	}
	
	
	public void setAction ( Action action ) {
		
		this.action = action;
	}
	
	public void setName( String name ) {
		
		this.name = name;
	}
	
	public String getName () {
		
		return name;
	}
	
	public String getDescription() {
		
		return description;
	}
	
	
	public List<Expression> getExpressionList() {
		
		return expressionList;
	}
	
	public Action getAction() {
		
		return action;
	}
	
	
}
