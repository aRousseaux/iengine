package KnowledgeBase;

import java.util.ArrayList;
import java.util.List;

public class Clause extends HornClause
{
	private String fLeft;
	private List<String> fLeftLiterals;
	private String fDirectional;
	private String fRight;
	private int fCount;
	
	public Clause(String aClauseString)
	{
		// assume right implication
		fDirectional = "=>";
		String[] lClauses = aClauseString.split(fDirectional);
		fLeft = lClauses[0]; 
		fRight = lClauses[1];
		
		String[] lLeftLiterals = fLeft.split("&");
		fLeftLiterals = new ArrayList<String>();
		for ( String lLiteral : lLeftLiterals )
		{
			fLeftLiterals.add( lLiteral );
		}
		
		fCount = fLeft.split("&").length;
	}
	
	public String toString()
	{
		return "Clause: " + fLeft + fDirectional + fRight;
	}
	
	public int getCount()
	{
		return fCount;
	}
	
	public void decrementCount()
	{
		fCount--;
	}
	
	public void incrementCount()
	{
		fCount++;
	}
	
	public String getLeftOperand()
	{
		return fLeft;
	}
	
	public String getRightOperand()
	{
		return fRight;
	}
	
	public List<String> getLiterals()
	{
		List<String> lResult = new ArrayList<String>();
		
		String[] lLiterals = fLeft.split("&");

		for ( String lLiteral : lLiterals )
		{
			lResult.add( lLiteral );
		}
		
		lResult.add( fRight );
		
		return lResult;
	}
	
	public List<String> getLeftLiterals()
	{
		return fLeftLiterals;
	}
}
