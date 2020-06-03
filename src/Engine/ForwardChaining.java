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
		if ( PL_FC_Entails() )
		{
			StringBuilder sb = new StringBuilder();
			sb.append("YES: ");
			
			for (Map.Entry<String, Boolean> lLiteral : fInferred.entrySet())
			{
				if (lLiteral.getValue())
				{
					sb.append(lLiteral.getKey());
					sb.append(",");
				}
			}
			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		}
		else
		{
			return "No";
		}
	}
	
	private boolean PL_FC_Entails()
	{
		while ( !( fAgenda.isEmpty() ) )
		{
			String lPremise = fAgenda.pop();
			
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
							if ( lSentence.getRightOperand().equals(fQuery) )
							{
								fInferred.replace(lSentence.getRightOperand(), true);
								return true;
							}
							
							fAgenda.addLast( lSentence.getRightOperand() );
						}
					}
				}
			}
		}
		
		System.out.println(fInferred);
		return false;
	}
}
