package Engine;

import KnowledgeBase.*;

public abstract class Algorithm 
{
	private String fName;
	protected String fQuery;
	protected KnowledgeBase fKnowledge;
	
	public Algorithm( KnowledgeBase aKnowledge, String aQuery, String aName )
	{
		fName = aName;
		
		fQuery = aQuery;
		fKnowledge = aKnowledge;
		
		System.out.print( toString() );
		
		System.out.println( "Literals:\n" + aKnowledge.getLiterals() );
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append( "Algorithm:\n" + fName + "\n\n" );
		sb.append( "Knowledge:\n" + fKnowledge + "\n" );
		sb.append( "Asking:\n" + fQuery + "\n\n" );
		
		return sb.toString();
	}
	
	public abstract String execute();
	
	/*private boolean isFact( String aQuery )
	{
		for ( Fact lFact : fKnowledge.getFacts() )
		{
			if ( lFact.isEqual( aQuery ) )
				return true;
		}
		
		return false;
	}*/
}
