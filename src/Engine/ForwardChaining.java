package Engine;

import java.util.HashMap;
import java.util.LinkedList;

import KnowledgeBase.*;

public class ForwardChaining extends Algorithm
{
	private LinkedList<String> fAgenda;
	private HashMap<String, Boolean> fInferred;
	
	public ForwardChaining(KnowledgeBase aKnowledgeBase, String aQuery) 
	{
		super(aKnowledgeBase, aQuery, "Forward Chaining");
		
		fAgenda = new LinkedList<String>();
		for ( Fact lFact : fKnowledge.getFacts() )
		{
			fAgenda.add( lFact.asString() );
		}
		
		fInferred = new HashMap<String, Boolean>();
		for (String lLiteral : fKnowledge.getLiterals())
		{
			fInferred.put(lLiteral, false);
		}
	}

	public String execute() 
	{
		while ( !( fAgenda.isEmpty() ) )
		{
			String lPremise = fAgenda.pop();
			System.out.println("Now inferring " + lPremise);
			
			if ( !fInferred.get(lPremise) )
			{
				fInferred.replace(lPremise, true);
				
				for ( Clause lSentence : fKnowledge.getClauses() )
				{
					if ( lSentence.getLeftLiterals().contains(lPremise) )
					{
						lSentence.decrementCount();
						if ( lSentence.getCount() == 0 )
						{
							if ( lSentence.getRightOperand() == fQuery )
							{
								fInferred.replace(lSentence.getRightOperand(), true);
								
								return "YES";
							}
							
							fAgenda.addLast( lSentence.getRightOperand() );
						}
					}
				}
			}
		}
		
		return "NO";
	}
}
