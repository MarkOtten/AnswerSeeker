package answerseeker;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class StackOverflow extends WebsiteBase{

	private String baseStackoverflow = "http://stackoverflow.com";
	private String baseStackOverflowSearchString = "/search?q=";
	private String searchResultAnswerClass = "result-link";
	
	
	public void searchResults(String searchTopic){
		activeThreads.incrementAndGet();
		Thread thread = new Thread(new Runnable() {
		    public void run() {	
		    	try {
		    		// Initially (early 2014 when this was built) Jsoup.connect(baseStackOverflowSearchString + searchTopic).get() worked for retrieving a page, now this fails (December 2014)
		    		// In order for the program to function, one must use Chrome, Firefox, ect. as a user agent. The reason is that stackoverflow disallows web crawlers
		    		Elements results = Jsoup.connect(baseStackoverflow + baseStackOverflowSearchString + searchTopic.replace("+","%2B").replace(" ", "+")).userAgent("Chrome").get().getElementsByClass(searchResultAnswerClass);
			
		    		for (Element link : results)
		    			links.add(baseStackoverflow + link.select("a").first().attr("href"));
			
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	}
		    	finally{
		    		activeThreads.decrementAndGet();
		    	}
		    }
		});
		thread.start();
	}
	
	
	
	protected void parseAnswerPage(String link){
		activeThreads.incrementAndGet();
		Thread thread = new Thread(new Runnable() {
		    public void run() {
		    	try {
		    		Document page = Jsoup.connect(link).userAgent("Chrome").get();
		    		Elements answerCell = page.getElementsByClass("answercell");
		    		Elements votes = page.getElementsByClass("vote").select("span");
		    		int counter = 0;

		    		//remove the first element as  it is for the question
		    		if(votes.size() > 0)
		    			votes.remove(0);
				
		    		for(Element part : answerCell){
		    			int voteCount = (int)removeCommaGetFloat(votes.get(counter).ownText());
		    			float userRank = 0;
				
		    			for(Element repScore : part.getElementsByClass("user-details").select("span.reputation-score"))
		    				userRank = removeCommaGetFloat(repScore.ownText());
		    			
		    			Answer newOne = new Answer(userRank,voteCount,part.select("p,code,li").toString(),link);
		    			answers.add(newOne);
				
		    			counter++;
		    		}
		    	}
		    	catch (IOException e) {
		    		e.printStackTrace();
		    	}
		    	finally{
		    		activeThreads.decrementAndGet();
		    	}
		    }
		});
		thread.start();
	}
	
	
}
