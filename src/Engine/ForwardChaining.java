package Engine;

import java.util.List;

import KnowledgeBase.*;;

public class ForwardChaining extends Algorithm
{
	private List<Fact> fAgenda;
	
	public ForwardChaining(KnowledgeBase aKnowledgeBase, String aQuery) 
	{
		super(aKnowledgeBase, aQuery, "Forward Chaining");
		
		fAgenda = fKnowledge.getFacts();
	}

	// https://www.iiia.csic.es/~puyol/IAGA/Teoria/07-AgentsLogicsII.pdf
	public String execute() 
	{
		for ( Fact lFact : fAgenda )
		{
			if ( lFact.isEqual( fQuery ) )
				return "YES";
		}
		
		HornClause lPremise;
		
		while ( !( fAgenda.isEmpty() ) )
		{
			lPremise = fAgenda.remove(0);
			System.out.println( "Now inferring: " + lPremise );
		}
		
		return "NO";
	}
}
