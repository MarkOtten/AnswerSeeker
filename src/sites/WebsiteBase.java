package answerseeker;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public abstract class WebsiteBase {

	//spins off a thread to get the search page
	public abstract void searchResults(String seachTopic);
	//spins off a thread that parses one of the answer pages
	protected abstract void parseAnswerPage(String link);
	
	//keeps track of the number of current threads running
	protected AtomicInteger activeThreads = new AtomicInteger(0);
	//the parsed answers for the particular website
	protected List<Answer> answers;
	//links to the threads on the website
	protected LinkedList<String> links;
	
	
	WebsiteBase(){
		answers =  Collections.synchronizedList(new ArrayList<Answer>());
		links = new LinkedList<String>();
	}
	
	
	
	public boolean childThreadsComplete(){
		if(activeThreads.get() == 0)
			return true;

		return false;
	}
	
	
	
	public boolean parseNextThread(){
		if(links.size() > 0){
			parseAnswerPage(links.get(0));
			links.remove(0);
			return true;
		}
		else
			return false;
	}
	
	
	
	public boolean answerAvailable(){	
		if(answers.size() > 0)
			return true;
		else
			return false;
	}
	
	
	
	public Answer getAnswer(){
		if(answerAvailable()){
			Answer ans = answers.get(0);
			answers.remove(0);
			return ans;
		}
		System.err.println("Always check answerAvailable() before calling getAnswer(). answers is empty returning an empty answer");
		return new Answer(0,0,"","");
	}
	
	
	
	protected float removeCommaGetFloat(String val){
		try{
			val = val.replace(",", "");
			
			if(val.contains("k"))
				return Float.parseFloat(val.replace("k", "")) * 1000;
			else if(val.contains("K"))
				return Float.parseFloat(val.replace("K", "")) * 1000000;
			else if(val.contains("M"))
				return Float.parseFloat(val.replace("M", "")) * 1000000;
			else
				return Float.parseFloat(val);	
		}
		catch(Exception e){
			System.err.println("Error parsing string: \"" + val + "\" to float. returning 0");
			return 0;
		}	
	}
		
	
}