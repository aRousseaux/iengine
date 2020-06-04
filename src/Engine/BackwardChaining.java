package Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
		if ( FOL_BC_ASK() != null )
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

	private List<String> FOL_BC_ASK()
	{
		return FOL_BC_OR(new ArrayList<String>());
	}
	
	private List<String> FOL_BC_OR(List<String> aTheta)
	{
		for (String lRule : FETCH_RULES_FOR_GOAL() )
		{
			
		}
		return null;
	}
	
	private List<String> FETCH_RULES_FOR_GOAL()
	{
		return null;
	}
	
	private List<String> STANDARDIZE_VARIABLES(List<String> aLHSRHS)
	{
		return null;
	}
}
