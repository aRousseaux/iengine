package Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import KnowledgeBase.Clause;
import KnowledgeBase.Fact;
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
		// agenda starts with all known facts (data driven)
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
			System.out.println(fFacts);
			System.out.println(fAgenda);
			System.out.println(noProvedPath);
			System.out.println(provedPath);
			StringBuilder sb = new StringBuilder();
			sb.append("YES: ");
			
			for (String lAnswer : noProvedPath)
			{
				sb.append(lAnswer);
				sb.append(",");
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

	private boolean FOL_BC_ASK()
	{
		// first find the path of the query
		// continue until nothing in agenda
		while ( !fAgenda.isEmpty() )
		{
			// grab the first thing on the agenda
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
		
		FOL_BC_COMPARE();
		FOL_BC_COMPARE();
		
		Collections.reverse(noProvedPath);
		List<String> notSecond = new ArrayList<String>();
		List<String> notFirst = new ArrayList<String>();
		
		//If noProvedPath and provedPath are same then its true
		for (String lPath : noProvedPath)
		{
			if (!provedPath.contains(lPath))
				notSecond.add(lPath);
		}
		for (String lPath : provedPath)
		{
			if (!noProvedPath.contains(lPath))
				notFirst.add(lPath);
		}
		
		
		return notSecond.isEmpty() && notFirst.isEmpty();
	}
	// function that prove the path
	private void FOL_BC_COMPARE()
	{
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
								for (Fact lFact : fKnowledge.getFacts())
								{
									if (lFact.isEqual(lSymbol))
									{
										lSentence.decrementCount();
										// if all symbols are inferred before the implication
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
		}
	}
}
