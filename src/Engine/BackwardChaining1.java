package Engine;

import java.util.LinkedList;

import KnowledgeBase.Clause;
import KnowledgeBase.Fact;
import KnowledgeBase.KnowledgeBase;

public class BackwardChaining1 extends Algorithm
{
	private LinkedList<String> fAgenda;
	private LinkedList<String> fChain;
	
	public BackwardChaining1(KnowledgeBase aKnowledgeBase, String aQuery) 
	{
		super(aKnowledgeBase, aQuery, "Backward Chaining");
		
		fAgenda = new LinkedList<String>();
		fChain = new LinkedList<String>();
	}

	public String execute() 
	{
		if ( PL_BC_ASK() )
		{
			StringBuilder sb = new StringBuilder();
			sb.append("YES: ");
			
			for ( String lCheck : fChain )
			{
				sb.append( lCheck );
				sb.append( "," );
			}
			sb.deleteCharAt(sb.length()-1);
			
			return sb.toString();
		}
		else
		{
			return "NO";
		}
	}

	private boolean PL_BC_ASK()
	{
		fChain.push(fQuery);
		
		
		for( Fact lFact : fKnowledge.getFacts() )
		{
			if ( lFact.isEqual(fQuery) )
			{
				return true;
			}
		}
		
		
		LinkedList<String> lIDs = new LinkedList<String>();
		lIDs.push(fQuery);
		String lCurrent; 
		
		for ( int i = 0; i < fKnowledge.getFacts().size(); i++)
		{
			if ( !lIDs.isEmpty() )
			{
				break;
			}

			lCurrent = fAgenda.peekFirst();
		}
		
		return false;
	}
}
