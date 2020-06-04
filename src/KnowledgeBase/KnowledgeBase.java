package KnowledgeBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class KnowledgeBase 
{
	private List<HornClause> fClauses;
	private HashSet<String> fLiterals;
	
	public KnowledgeBase(String aTell)
	{
		String[] lSentences = aTell.replaceAll("\\s", "").split(";");
		
		fClauses = new ArrayList<HornClause>();
		fLiterals = new HashSet<String>();
		
		for (String aString : lSentences)
		{
			if (aString.contains("=>"))
			{
				fClauses.add( new Clause(aString) );
			}
			else
			{
				fClauses.add( new Fact(aString) );
			}
		}
		
		updateLiterals();
	}
	
	public List<Fact> getFacts()
	{
		List<Fact> lResult = new ArrayList<Fact>();
		
		for ( HornClause lClause : fClauses )
		{
			if ( lClause instanceof Fact )
				lResult.add( (Fact) lClause );
		}
		
		return lResult;
	}
	
	public List<Clause> getClauses()
	{
		List<Clause> lResult = new ArrayList<Clause>();
		
		for ( HornClause lClause : fClauses )
		{
			if ( lClause instanceof Clause )
				lResult.add( (Clause) lClause );
		}
		
		return lResult;
	}
	
	public List<HornClause> getAll()
	{
		return fClauses;
	}
	
	public HashSet<String> getLiterals()
	{
		return fLiterals;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for ( HornClause lClause : fClauses )
		{
			sb.append( lClause );
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	private void updateLiterals()
	{
		for ( HornClause lClause : fClauses )
		{
			fLiterals.addAll( lClause.getLiterals() );
		}
	}
}
