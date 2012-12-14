package tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.thunlp.io.TextFileReader;

import bean.ConfiFile;

public class LoadDictionary {
	public static void main(String[] args){
		LoadDictionary loaddict = new LoadDictionary();
		loaddict.loadDictionary(ConfiFile.CEDictionary);
	}
	public Map<String,ArrayList<WordProPair> >loadDictionary(String dictFilePath){
		Map<String ,ArrayList< WordProPair> > dictionary = new HashMap<String, ArrayList<WordProPair>>();
		try {
			TextFileReader dictReader = new TextFileReader(dictFilePath);
			String line ;
			ArrayList<WordProPair> pairlist = new ArrayList<WordProPair>();
			String lastSourceWord ="";
			while((line = dictReader.readLine()) != null){
				
				String[] parts = line.split(" ");
				if(!lastSourceWord.equals(parts[0])){//到了新词
					if(pairlist.size() != 0){
						dictionary.put(lastSourceWord, pairlist);
						/*for(WordProPair p : pairlist){
							System.out.println(lastSourceWord+" "+ p.CorreWord+" "+p.Prob);
						}*/
						pairlist = new ArrayList<WordProPair>();
					}
					lastSourceWord = parts[0];
				}
				pairlist.add(new WordProPair(parts[1],new Double(parts[2])));
			}
			if(pairlist.size() != 0)
				dictionary.put(lastSourceWord, pairlist);
			dictReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return dictionary;
	}
	public Map<String,ArrayList<WordProPair> > loadPhraseDictionary(String phraseFilePath){
		Map<String ,ArrayList< WordProPair> > dictionary = new HashMap<String, ArrayList<WordProPair>>();
		try {
			TextFileReader dictReader = new TextFileReader(phraseFilePath);
			String line ;
			ArrayList<WordProPair> pairlist = new ArrayList<WordProPair>();
			String lastSourceWord ="";
			while((line = dictReader.readLine()) != null){
				
				String[] parts = line.split(ConfiFile.SORTED_PHRASE_TABLE_SEPARATOR);
				if(!lastSourceWord.equals(parts[0])){//到了新词
					if(pairlist.size() != 0){
						dictionary.put(lastSourceWord, pairlist);
						/*for(WordProPair p : pairlist){
							System.out.println(lastSourceWord+" "+ p.CorreWord+" "+p.Prob);
						}*/
						pairlist = new ArrayList<WordProPair>();
					}
					lastSourceWord = parts[0];
				}
				pairlist.add(new WordProPair(parts[1],new Double(parts[2])));
			}
			if(pairlist.size()!= 0)
				dictionary.put(lastSourceWord, pairlist);
			dictReader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return dictionary;
	}
}
