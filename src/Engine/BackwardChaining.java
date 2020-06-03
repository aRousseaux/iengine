package Engine;

import KnowledgeBase.KnowledgeBase;

public class BackwardChaining extends Algorithm
{
	public BackwardChaining(KnowledgeBase aKnowledgeBase, String aQuery) 
	{
		super(aKnowledgeBase, aQuery, "Backward Chaining");
	}

	public String execute() 
	{
		if ( PL_BC_Entails() )
		{
			return "YES";
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
