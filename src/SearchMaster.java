package answerseeker;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class SearchMaster {

	private PrintWriter writer = null;
	//the maximum number of results to output
	private int maxOutput = 10;
	
	
	public void questionSearch(String question,String fileName,boolean outputType ){
		if(outputType)
			outputType = initializeWriter(fileName);
		
		ArrayList<Answer> answers =  searchResults(question);

		outputLines(outputType,answers);
		
		if(writer != null)
			writer.close();
	}
	
	
	
	private void outputLines(boolean output,ArrayList<Answer> answers){
		int i = 0;
		
		addOutputLine("<li>",output);
		while(i < maxOutput && i < answers.size()){
			addOutputLine(Integer.toString(i+1) + ".",output);
			
			int j = 0;
			while( j <  answers.get(i).getAnswer().length && j < 9){
				addOutputLine(answers.get(i).getAnswer()[j],output);
				j++;
			}
		
			if( answers.get(i).getAnswer().length > 8)
				addOutputLine("..................................",output);
			
			addOutputLine(answers.get(i).getPagelink(),output);
			addOutputLine("<hr>",output);
			i++;
		}
		addOutputLine("</li>",output);
	}
	
	
	
	private void addOutputLine(String line,boolean output){
		if(output && writer != null)
			writer.println(line); 
		else 
			System.out.println(line);
	}
	
	
	
	private boolean initializeWriter(String fileName){
		try{
			writer = new PrintWriter(fileName, "UTF-8");
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
			return false;
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	private ArrayList<Answer> searchResults(String searchTopic){
		StackOverflow stackOver = new StackOverflow();
		TreeSet<Answer> stackOverRes = new TreeSet<Answer>();
		CodeProject codeProj = new CodeProject();
		TreeSet<Answer> codeProjRes = new TreeSet<Answer>();		
		int iterations = 0,pagesToParse = 3;


		codeProj.searchResults(searchTopic);
		stackOver.searchResults(searchTopic);
		
		while(!stackOver.childThreadsComplete() && !codeProj.childThreadsComplete())
			sleep(100);

		while( iterations < pagesToParse && (stackOver.parseNextThread() | codeProj.parseNextThread()));
			iterations++;

		while(!stackOver.childThreadsComplete() || !codeProj.childThreadsComplete())
			sleep(100);
		
		//adding the answers to the trees
		while( (codeProj.answerAvailable() || stackOver.answerAvailable()) ){			
			if(codeProj.answerAvailable())
				codeProjRes.add (codeProj.getAnswer());
		
			if(stackOver.answerAvailable())
				stackOverRes.add(stackOver.getAnswer());
		}
		
		return sortResults(stackOverRes,codeProjRes);
	}
	
	
	
	// as the ranking systems between stackoverflow and codeproject are vastly different. limiting codeproject to a percentage of the stackoverflow max score
	// produces good results. Used stackoverflow to set the bar, as their answers are on average of better quality that those from codeproject
	private ArrayList<Answer> sortResults(TreeSet<Answer> stackOverRes ,TreeSet<Answer> codeProjRes){
		ArrayList<Answer> results = new ArrayList<Answer>();

		float codeProjMax = (stackOverRes.size() > 0)?stackOverRes.last().getRanking() *.8f:(codeProjRes.size() > 0)?codeProjRes.last().getRanking():0.0f;
		while(codeProjRes.size() > 0 || stackOverRes.size() > 0){
			if(codeProjRes.size() > 0 && stackOverRes.size() > 0){
				if( adjustedScore(codeProjRes.last().getRanking(),codeProjMax) > stackOverRes.last().getRanking())
					results.add(codeProjRes.pollLast());
				else
					results.add(stackOverRes.pollLast());
			}
			else if(codeProjRes.size() > 0)
				results.add(codeProjRes.pollLast());
			else
				results.add(stackOverRes.pollLast());
		}
		return results;
	}
	
	
	
	private void sleep(int mil_time){
		try {
		    Thread.sleep(mil_time);                
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	
	
	private float adjustedScore(float score,float max){
		return (score > max)?max:score;
	}
	
	
}