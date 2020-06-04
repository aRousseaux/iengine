package Engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import KnowledgeBase.Clause;
import KnowledgeBase.KnowledgeBase;

public class BackwardChaining extends Algorithm
{
	private LinkedList<String> fAgenda;
	private List<String> fFacts;
	private List<String> noProvedPath;
	private List<String> provedPath;
	
	public BackwardChaining(KnowledgeBase aKnowledgeBase, String aQuery) 
	{
		super(aKnowledgeBase, aQuery, "Backward Chaining");
		
		fAgenda = new LinkedList<String>();
		fAgenda.push( fQuery );
		
		fFacts = new ArrayList<String>();
		noProvedPath = new ArrayList<String>();
		provedPath = new ArrayList<String>();
	}

	public String execute() 
	{
		FOL_BC_ASK();
		
		if ( !noProvedPath.isEmpty() )
		{
			StringBuilder sb = new StringBuilder();
			sb.append("YES: ");
			
			for (String lAnswer : noProvedPath)
			{
				sb.append(lAnswer);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		}
		else
		{
			return "NO";
		}
	}

	private boolean FOL_BC_ASK()
	{
		while ( !fAgenda.isEmpty() )
		{
			String lPath = fAgenda.pop();
			if (!noProvedPath.contains(lPath))
			{
				noProvedPath.add(lPath);
			}
			if (!fFacts.contains(lPath))
			{
				List<Clause> queryContainsPath = new ArrayList<Clause>();
				
				for (Clause lSentence : fKnowledge.getClauses())
				{
					if (lSentence.getRightOperand().equals(lPath))
					{
						queryContainsPath.add(lSentence);
					}
				}
				if (!queryContainsPath.isEmpty())
				{
					for (Clause lSentence : queryContainsPath)
					{
						for (String lSymbol : lSentence.getLeftLiterals())
						{
							if (!noProvedPath.contains(lSymbol))
							{
								fAgenda.push(lSymbol);
							}
						}
					}
				}
			}
		}
		
		for (String lSymbol : noProvedPath)
		{
			fAgenda.push(lSymbol);
		}
		
		while ( !fAgenda.isEmpty() )
		{
			String lPath = fAgenda.pop();
			if (fFacts.contains(lPath))
			{
				provedPath.add(lPath);
			}
			else
			{
				for ( Clause lSentence : fKnowledge.getClauses() )
				{
					if (!provedPath.contains(lPath))
					{
						if (lSentence.getRightOperand().equals(lPath))
						{
							for (String lSymbol: lSentence.getLeftLiterals())
							{
								if (fKnowledge.getFacts().contains(lSymbol))
								{
									lSentence.decrementCount();
									if (lSentence.getCount() == 0)
									{
										fFacts.add(lPath);
										fAgenda.push(lPath);
									}
								}
							}
						}
					}
				}
			}
		}
		
		return true;
	}
}
