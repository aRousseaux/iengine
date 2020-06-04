package Engine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import KnowledgeBase.*;

public class ForwardChaining extends Algorithm
{
	private LinkedList<String> fAgenda;
	private HashMap<String, Boolean> fInferred;
	
	public ForwardChaining(KnowledgeBase aKnowledgeBase, String aQuery) 
	{
		super(aKnowledgeBase, aQuery, "Forward Chaining");
		
		// agenda starts with all known facts (data driven)
		fAgenda = new LinkedList<String>();
		for ( Fact lFact : fKnowledge.getFacts() )
		{
			fAgenda.add( lFact.asString() );
		}
		
		// create a map of all symbols to booleans (initially false)
		fInferred = new HashMap<String, Boolean>();
		for (String lLiteral : fKnowledge.getLiterals())
		{
			fInferred.put(lLiteral, false);
		}
	}

	public String execute() 
	{
		// if the query can be entailed
		if ( PL_FC_Entails() )
		{
			StringBuilder sb = new StringBuilder();
			sb.append("YES: ");
			
			// add all of the inferred symbols to the output
			for (Map.Entry<String, Boolean> lLiteral : fInferred.entrySet())
			{
				if (lLiteral.getValue())
				{
					sb.append(lLiteral.getKey());
					sb.append(",");
				}
			}
			
			// remove final comma
			sb.deleteCharAt(sb.length()-1);
			
			return sb.toString();
		}
		else
		{
			return "NO";
		}
	}
	
	private boolean PL_FC_Entails()
	{
		// continue until nothing in agenda
		while ( !( fAgenda.isEmpty() ) )
		{
			// grab the first thing on the agenda
			String lPremise = fAgenda.pop();
			
			// if it is not already inferred
			if ( !fInferred.get(lPremise) )
			{
				// infer it
				fInferred.replace(lPremise, true);
				
				// for all clauses in the knowledge base
				for ( Clause lSentence : fKnowledge.getClauses() )
				{
					// if the current premise is before the implication
					if ( lSentence.getLeftLiterals().contains(lPremise) )
					{
						lSentence.decrementCount();
						
						// if all symbols are inferred before the implication
						if ( lSentence.getCount() == 0 )
						{
							if ( lSentence.getRightOperand().equals(fQuery) )
							{
								// add the right of the implication to inferred
								fInferred.replace(lSentence.getRightOperand(), true);
								return true;
							}
							
							// otherwise, put it on the end of the agenda (to come back to)
							fAgenda.addLast( lSentence.getRightOperand() );
						}
					}
				}
			}
		}
		
		// if nothing in the agenda, no possible solution
		return false;
	}
}
