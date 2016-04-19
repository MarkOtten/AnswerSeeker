package answerseeker;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CodeProject extends WebsiteBase {

	private String baseUrl = "http://www.codeproject.com/";
	private String searchBefore = "/search.aspx?q=";
	private String searchAfter = "&doctypeid=4";
	
	
	public void searchResults(String searchTopic){
		activeThreads.incrementAndGet();
		Thread thread = new Thread(new Runnable() {
		    public void run() {			
		    	try {
		    		Elements answerCell = Jsoup.connect(baseUrl + searchBefore + searchTopic.replace("+","%2B").replace(" ", "+") + searchAfter).userAgent("Chrome").get().getElementsByClass("entry").select("span[class=title]").select("a");
			
		    		for(Element part : answerCell)
		    			links.add(("http://" + part.select("a").first().attr("href").substring(2) ) );
			
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
		    	ArrayList<Float> raterScore = new ArrayList<Float>();
		    	try{
		    		Document page = Jsoup.connect(link).get();
		    		Elements answerCells = page.select("div[class=text]");
		    		Elements raterScores = page.select("div[class=member-rep-container]");
		    		
		    		for(Element score:raterScores){
		    			if(score.select("span").toString().contains("Answer"))
		    				raterScore.add(removeCommaGetFloat(score.select("span").text()));
		    		}
				
		    		for(Element ans: answerCells){
		    			Answer a = new Answer(raterScore.get(0),0,ans.select("div").last().toString(),link);
		    			answers.add(a);
		    			if(raterScore.size() > 0)
		    				raterScore.remove(0);
		    		}
		    	}
		    	catch(IOException e){
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
