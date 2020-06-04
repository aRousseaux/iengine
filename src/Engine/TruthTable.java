package Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import KnowledgeBase.Clause;
import KnowledgeBase.KnowledgeBase;

public class TruthTable extends Algorithm
{
	private List<String> fLiterals;
	private int fCount;
	
	public TruthTable(KnowledgeBase aKnowledgeBase, String aQuery) 
	{
		super(aKnowledgeBase, aQuery, "Truth Table");
		
		fLiterals = new ArrayList<String>();
		for ( String lLiteral : fKnowledge.getLiterals() )
		{
			fLiterals.add( lLiteral );
		}
		
		fCount = 0;
	}

	public String execute() 
	{
		TT_Entails();
		
		if (fCount > 0)
		{
			return "YES: " + fCount;
		}
		else
		{
			return "NO";
		}
	}
	
	private boolean TT_Entails()
	{
		return TT_CHECK_ALL( fLiterals, new HashMap<String, Boolean>() );
	}
	
	@SuppressWarnings("unchecked")
	private boolean TT_CHECK_ALL( List<String> aSymbols, HashMap<String, Boolean> aModel )
	{
		if ( aSymbols.isEmpty() )
		{
			if ( PL_TRUE( aModel ) )
			{
				fCount++;
				return PL_TRUE( fQuery, aModel );
			}
			else
			{
				// when KB is false, always return true
				return true;
			}
		}
		else
		{
			String lP = aSymbols.get(0);
			List<String> lRest = aSymbols;
			lRest.remove(0);
			
			HashMap<String, Boolean> lTrueModel = (HashMap<String, Boolean>) aModel.clone(); 
			lTrueModel.put(lP, true);
			HashMap<String, Boolean> lFalseModel = (HashMap<String, Boolean>) aModel.clone();
			lFalseModel.put(lP, false);
			
			return ( TT_CHECK_ALL(lRest, lTrueModel ) ) && ( TT_CHECK_ALL(lRest, lFalseModel ) );
		}
	}
	
	// returns true if a sentence holds within a model
	private boolean PL_TRUE( HashMap<String, Boolean> aModel )
	{
		boolean results = true;
		
		for ( Clause lSentence : fKnowledge.getClauses() )
		{
			boolean lLeft = true;
			boolean lRight = PL_TRUE( lSentence.getRightOperand(), aModel );
			boolean result = false;
			
			if (lSentence instanceof Clause)
			{
				if ( lSentence.getCount() == 1 )
				{
					lLeft = PL_TRUE(lSentence.getLeftOperand(), aModel);
				}
				else if ( lSentence.getCount() > 1 )
				{
					for (String lLiteral : lSentence.getLeftLiterals())
					{
						lLeft = lLeft && PL_TRUE(lLiteral, aModel);
					}
				}
				
				if ( (lLeft == true) && (lRight == false) )
				{
					result = false;
				}
				else
				{
					result = true;
				}
			}
			else
			{
				result = lRight;
			}
			
			if (result == false)
			{
				results = false;
				break;
			}
		}

		return results;
	}
	
	// returns true if a sentence holds within a model
	private boolean PL_TRUE( String aQuery, HashMap<String, Boolean> aModel )
	{
		for( Map.Entry<String, Boolean> lEntry : aModel.entrySet() )
		{
			if ( aQuery.equals( lEntry.getKey() ) )
			{
				return lEntry.getValue();
			}
		}
		
		return false;
	}
}
