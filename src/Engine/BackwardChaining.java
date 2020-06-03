package Engine;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import KnowledgeBase.Fact;
import KnowledgeBase.KnowledgeBase;

public class BackwardChaining extends Algorithm
{
	private LinkedList<String> fAgenda;
	private HashMap<String, Boolean> fInferred;
	
	public BackwardChaining(KnowledgeBase aKnowledgeBase, String aQuery) 
	{
		super(aKnowledgeBase, aQuery, "Backward Chaining");
		
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
		if ( PL_BC_Entails() )
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
			return "NO";
		}
	}

	private boolean PL_BC_Entails()
	{
		return false;
	}
}
