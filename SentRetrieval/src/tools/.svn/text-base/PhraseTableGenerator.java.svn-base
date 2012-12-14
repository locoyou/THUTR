package tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.thunlp.io.TextFileReader;
import org.thunlp.io.TextFileWriter;
import org.thunlp.mt.wa.phrase.PhraseTableWithFrequency;


import bean.ConfiFile;
/**
 * 先调用sort
 * 再调用filter
 * @author flaming
 *
 */
public class PhraseTableGenerator {
	public Map<String,ArrayList<WordProPair>> PhraseDictionary = new HashMap<String,ArrayList<WordProPair>>();
	/**
	 * 过滤掉那些中文短语中的标点符号和英文中的标点符号,产生最终的短语表
	 */
	public void filter(){
		try {
			TextFileReader phraseReader = new TextFileReader("sortedPhraseTable.txt");
			TextFileWriter phraseWriter = new TextFileWriter(ConfiFile.FINAL_PHRASE_TABLE);
			String line = "";
			while((line = phraseReader.readLine()) != null){
				String[] parts = line.split(ConfiFile.SORTED_PHRASE_TABLE_SEPARATOR);
				String[] source = parts[0].split(" ");
				String[] target = parts[1].split(" ");
				String finalSource = "";
				String finalTarget = "";
				for(int i = 0; i < source.length ; i++){
					String sourceWord = source[i];
					if(!sourceWord.matches(ConfiFile.CHINESE_PUNCTUATION) && !sourceWord.matches(ConfiFile.LUCENE_FILT_CHARACTOR)){
						if(i < source.length -1)
							finalSource += sourceWord +" ";
						else
							finalSource += sourceWord;
					}
				}
				
				for(int i = 0; i < target.length; i++){
					String targetWord = target[i];
					if(!targetWord.matches(ConfiFile.ENGLISH_PUNCTUATION)){
						if(i < target.length-1)
							finalTarget += targetWord + " ";
						else
							finalTarget += targetWord;
					}
				}
				if(!finalTarget.equals("") && !finalSource.equals(""))
					phraseWriter.writeLine(finalSource +ConfiFile.SORTED_PHRASE_TABLE_SEPARATOR 
							+ finalTarget+ ConfiFile.SORTED_PHRASE_TABLE_SEPARATOR + parts[2]);
				
				
			}
			phraseReader.close();
			phraseWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 先排序再过滤
	 */
	
	public void sort(){
		File f = new File(ConfiFile.OrigPhraseTable);
		PhraseTableWithFrequency phraseTable = new PhraseTableWithFrequency() ;
		
		WordProComparator c = new WordProComparator();
		
		try {
			TextFileWriter writer = new TextFileWriter("sortedPhraseTable.txt");
			Map<String, Map<String, Integer>>phrase = phraseTable.build(f);
			
			Iterator<Entry<String, Map<String, Integer>>> entrySetIterator = phrase.entrySet().iterator();
			  
			while(entrySetIterator.hasNext()){
			      Entry<String,Map<String, Integer>> entry=entrySetIterator.next();
			      Map<String,Integer> map = entry.getValue();
			      
			      ArrayList<WordProPair> wordList = new ArrayList<WordProPair>();
			      for(Map.Entry<String, Integer> ent :map.entrySet()){   
			    	  wordList.add(new WordProPair(ent.getKey(),ent.getValue()));
			      }
			      Collections.sort(wordList,c);
			      PhraseDictionary.put(entry.getKey(), wordList);
			      for(WordProPair w: wordList){
			    	  writer.writeLine(entry.getKey()+ConfiFile.SORTED_PHRASE_TABLE_SEPARATOR+w.CorreWord+ConfiFile.SORTED_PHRASE_TABLE_SEPARATOR+w.Prob);
			      }
			  }
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args){
		PhraseTableGenerator gen = new PhraseTableGenerator();
		gen.sort();
		gen.filter();
	}
}
