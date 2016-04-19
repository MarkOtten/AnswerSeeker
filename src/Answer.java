package answerseeker;

public class Answer implements Comparable<Answer> {
	
	private float rank;
	private float userRank;
	private int voteCount;
	//the formatted (html) text string of the answer.
	private String answer[];
	private String pageLink;
	
	
	public Answer(float uRank,int vCount,String body,String link){
		userRank = uRank;
		
		voteCount = vCount;
		
		rank = userRank*.7f + (voteCount*(.3f*userRank));
		
		answer = body.split("\n");

		pageLink = "<br>\n <a href = "+ link + "> Link to source page.</a>\n<br>\n<br>";
	}
	
	
	
	public float getRanking(){
		return rank;
	}
	
	
	
	public String[] getAnswer(){
		return answer;
	}
	
	
	
	public String getPagelink(){
		return pageLink;
	}



	public int compareTo(Answer b) {
		return (rank > b.getRanking())?1:(rank == b.getRanking())?0:-1;
	}
	
	
}