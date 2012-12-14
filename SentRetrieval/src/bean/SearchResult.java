package bean;

public class SearchResult {
	public String Sent;
	public int SentIndex;
	public double score;
	public SearchResult(String sent,int sentindex, double score){
		this.Sent = sent;
		this.SentIndex = sentindex;
		this.score = score;
	}
	
	public String getSent(){
		return this.Sent;
	}
	public int getSentIndex(){
		return this.SentIndex;
	}
}
