package Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		if (TT_Entails())
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
	
	private boolean TT_CHECK_ALL( List<String> aSymbols, HashMap<String, Boolean> aModel )
	{
		if ( !aSymbols.isEmpty() )
		{
			if ( PL_TRUE( aModel ) )
			{
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
			
			HashMap<String, Boolean> lTrueModel = aModel; 
			lTrueModel.put(lP, true);
			HashMap<String, Boolean> lFalseModel = aModel; 
			lFalseModel.put(lP, false);
			
			return ( TT_CHECK_ALL(lRest, lTrueModel ) ) && ( TT_CHECK_ALL(lRest, lFalseModel ) );
		}
	}
	
	// returns true if a sentence holds within a model
	private boolean PL_TRUE( HashMap<String, Boolean> aModel )
	{
		return false;
	}
	
	// returns true if a sentence holds within a model
	private boolean PL_TRUE( String aQuery, HashMap<String, Boolean> aModel )
	{
		return false;
	}
}
