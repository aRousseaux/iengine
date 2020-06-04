package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Engine.*;
import KnowledgeBase.KnowledgeBase;

public class Main 
{
	public static void main( String[] args )
	{
		try 
		{
			Scanner lScanner = new Scanner( new File( args[1] ) );
			
			// TELL line
			lScanner.nextLine();
			// sentences line
			KnowledgeBase lKnowledgeBase = new KnowledgeBase( lScanner.nextLine() );
			// ASK line
			lScanner.nextLine();
			// to ask
			String lQuery = lScanner.nextLine();
			
			Algorithm lAlgorithm;
			
			switch( args[0] )
			{
			case "TT":
				lAlgorithm = new TruthTable(lKnowledgeBase, lQuery);
				break;
			case "FC":
				lAlgorithm = new ForwardChaining(lKnowledgeBase, lQuery);
				break;
			case "BC":
				lAlgorithm = new BackwardChaining(lKnowledgeBase, lQuery);
				break;
			default:
				lAlgorithm = new TruthTable(lKnowledgeBase, lQuery);
				break;
			}
			
			System.out.println( lAlgorithm.execute() );
			
			lScanner.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
