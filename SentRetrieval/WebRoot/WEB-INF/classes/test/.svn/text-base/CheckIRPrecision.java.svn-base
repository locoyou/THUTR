package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.thunlp.io.TextFileReader;

import tools.LoadDictionary;
import tools.WordProPair;

import bean.ChiConvertToEngBean;
import bean.ConfiFile;
import bean.SearchBean;

public class CheckIRPrecision {
	public static void main(String[] args){
		CheckIRPrecision c = new CheckIRPrecision();
		c.check();
	}
	/*public Map<String,ArrayList<WordProPair>> CEDictionary ;
	public Map<String,ArrayList<WordProPair>> ECDictionary ;
	public Map<String,ArrayList<WordProPair>> PhraseDictionary;
	
	public void init(){
		LoadDictionary loadDictionary = new LoadDictionary();
		System.out.println("Loading dictionary......");
		CEDictionary = loadDictionary.loadDictionary(ConfiFile.CEDictionary);
		ECDictionary = loadDictionary.loadDictionary(ConfiFile.ECDictionary);
		PhraseDictionary = loadDictionary.loadPhraseDictionary(ConfiFile.SORTED_PHRASE_TABLE);
		ArrayList<WordProPair> p = CEDictionary.get("一路");
		for(WordProPair pp : p){
			System.out.println(pp.toString());
		}
		p = CEDictionary.get("不");
		for(WordProPair pp : p){
			System.out.println(pp.toString());
		}
		System.out.println("C/E and E/C Dictionary loaded!");
		System.out.println("PhraseDictionary loaded!");
	}
	public double getEcpro(String engWord,String chiWord){
		for(WordProPair w : ECDictionary.get(engWord)){
			if(w.CorreWord.equals(chiWord))
				return w.Prob;
		}
		return 0;
	}
	
	public String convertToTargetSent(String sourceSent){
		if(CEDictionary!= null && ECDictionary!= null){
			String[] words = sourceSent.split(" ");
			String transSent = "";
			for(String word: words){
				if(word.matches("[，|。|、|？|：|；|！|‘|”|“|’|（|）|【|】|《|》|…|──|•|+]+"))
					continue;
				//transSent += CEDictionary.get(word).get(0).CorreWord+" ";
				double maxpro = 0;
				String selectedTargetWord="";
				for(WordProPair p : CEDictionary.get(word)){	// 实现查找p(e|c)+p(c|e)最大的英文单词
					double value;
					if((value = p.Prob + getEcpro(p.CorreWord,word)) > maxpro){
						maxpro = value;
						selectedTargetWord = p.CorreWord;
					}
				}
				
				transSent += selectedTargetWord+" ";
				maxpro = 0; 
				selectedTargetWord = "";
			}
			return transSent;
		}else
			return null;
	}*/
	
	
	public void check(){
		SearchBean searcher = new SearchBean();
		int totalRightAt10Count = 0;
		int totalRightAtFirstCount = 0;
		//init();
		int total = 1000;
		ChiConvertToEngBean converter = new ChiConvertToEngBean();
		try {
			TextFileReader chiReader = new TextFileReader(ConfiFile.SentForTestPath+"selectChiSentAfterWs");
			TextFileReader engReader = new TextFileReader(ConfiFile.SentForTestPath+"selectEngSentAfterTok");
			String line;
			int index = 0;
			while((line = chiReader.readLine()) != null){
				String engline = engReader.readLine();
				index ++;
				String[] parts = line.split(" _ : ");
				int sentIndex = new Integer(parts[0]).intValue();
				String irSent = parts[1];
				//String targSent = converter.convertToTargetSentUsePhrase(irSent);
				String targSent = converter.convertToTargetSent(irSent);
				System.out.println("--------------------------");
				System.out.println("Source Sentence: "+irSent);
				System.out.println("Target Sentence: "+engline);
				System.out.println("Query  Sentence: "+targSent);
				if( targSent.equals(""))
					continue;
				//差中英文翻译模块
				ArrayList<Integer> result = searcher.getIndexResult(targSent);
				if(result == null){
					total --;
					continue;
				}
				if(result.size() == 0){
					continue;
				}
				if(sentIndex == result.get(0))
					totalRightAtFirstCount ++;
				if(result.size()>10){
					for(int i = 0 ; i < 10 ; i++){
						if(sentIndex == result.get(i)){
							totalRightAt10Count ++;
							break;
						}
					}
				}else{
					for(int i = 0 ; i < result.size() ; i++){
						if(sentIndex == result.get(i)){
							totalRightAt10Count ++;
							break;
						}
					}
				}
				/*if(index > 5)
					break;*/
			}
			System.out.println("index "+index);
			System.out.println(totalRightAt10Count);
			System.out.println(totalRightAtFirstCount);
			
			chiReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.println();
			
			e.printStackTrace();
		}
		System.out.println("total success Query "+ total);
		System.out.println("precision at first 10 "+ ((double)totalRightAt10Count/(double)total)*100 +"%");
		System.out.println("precision at first " + ((double) totalRightAtFirstCount/(double)total)*100 +"%");
	}
}
