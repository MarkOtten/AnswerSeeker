package answerseeker;


import java.util.Scanner;


public class FrontEndSearch {
	
	public static void main(String[] args){

		SearchMaster searcher = new SearchMaster();
		
		String question = "",fileName;
				
		boolean outputType = false;
		
		Scanner scan = new Scanner(System.in);
		
		try{
			System.out.println("Enter a question: ");
			question = scan.nextLine();
			System.out.println("Enter filename for output, enter \"none\" to output to the console instead:");
			fileName = scan.nextLine();
			
			outputType = (fileName.equals("none"))?false:true;
		}
		catch(Exception e){
			System.err.println("Error reading input, now exiting.");
			scan.close();
			return;
		}

		scan.close();
		
		searcher.questionSearch(question,fileName,outputType);
	}

	
	
}