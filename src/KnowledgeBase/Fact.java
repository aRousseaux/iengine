package KnowledgeBase;

import java.util.ArrayList;
import java.util.List;

public class Fact extends HornClause
{
	private String fFact;
	
	public Fact( String aFact )
	{
		fFact = aFact;
	}
	
	public boolean isEqual( String aQuery )
	{
		return aQuery.equals( fFact );
	}
	
	public String toString()
	{
		return "Fact: " + fFact;
	}

	public String asString()
	{
		return fFact;
	}
	
	public String getRightOperand()
	{
		return fFact;
	}
	
	public List<String> getLiterals() 
	{
		List<String> lResult = new ArrayList<String>();
		lResult.add( fFact );
		return lResult;
	}
}
