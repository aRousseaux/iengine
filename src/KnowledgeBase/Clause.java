package KnowledgeBase;

import java.util.ArrayList;
import java.util.List;

public class Clause extends HornClause
{
	private String fLeft;
	private String fDirectional;
	private String fRight;
	
	public Clause(String aClauseString)
	{
		// assume right implication
		fDirectional = "=>";
		String[] lClauses = aClauseString.split(fDirectional);
		fLeft = lClauses[0]; 
		fRight = lClauses[1];
	}
	
	public String toString()
	{
		return "Clause: " + fLeft + fDirectional + fRight;
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
}
