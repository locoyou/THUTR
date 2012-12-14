package tools;

public class WordProPair {
	public String CorreWord;
	public double Prob;
	public WordProPair(String corrWord,double prob){
		this.CorreWord = corrWord;
		this.Prob = prob;
	}
	public String toString(){
		return this.CorreWord+ " "+this.Prob;
	}
}
