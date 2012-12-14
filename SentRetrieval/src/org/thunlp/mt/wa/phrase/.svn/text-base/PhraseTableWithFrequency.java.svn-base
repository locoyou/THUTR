package org.thunlp.mt.wa.phrase;
import java.io.*;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.ConfiFile;

/**
 * 将得到词表转换成 出现频度的格式
 * @author flaming
 * 
 */
public class PhraseTableWithFrequency {
	public Map<String, Map<String, Integer>> phraseTable = new HashMap<String, Map<String, Integer>>();
	Gson GSON_BUILDER = (new GsonBuilder()).disableHtmlEscaping().create();
	public void addEntry(String source, String target, int frequency) {
		if (phraseTable.containsKey(source))
			phraseTable.get(source).put(target, frequency);
		else {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put(target, frequency);
			phraseTable.put(source, map);
		}
	}
	
	/*public int[][] getAlignmentInfo(String source, String target) {
		if (phraseTable.containsKey(source))
			return phraseTable.get(source).get(target);
		else
			return null;
	}*/
	
	public boolean contains(String source, String target) {
		return phraseTable.containsKey(source)
			&& phraseTable.get(source).containsKey(target);
	}
	
	public Set<String> get(String source) {
		if (phraseTable.containsKey(source))
			return phraseTable.get(source).keySet();
		else
			return null;
	}
	
	public  Map<String, Map<String, Integer>> build(File file) throws IOException {
		
		BufferedReader reader;
		if (file != null)
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		else
			return null;
		
		String str;
		while((str = reader.readLine()) != null) {
			String[] parts = str.split(ConfiFile.PHRASE_TABLE_SEPERATOR_REG);
			BilingualPhraseWithAlignmentInfo phrase = 
				GSON_BUILDER.fromJson(parts[0], BilingualPhraseWithAlignmentInfo.class);
			Integer frequency = Integer.valueOf(parts[1]);
			//int[][] alignment = getAlignmentInfo(phrase.alignmentInfo);
			addEntry(phrase.source, phrase.target, frequency);
		}
		reader.close();
		return phraseTable ;
	}
	
	/*private static int[][] getAlignmentInfo(String alignmentStr) {
		String[] pairs = alignmentStr.split(" ");
		int[][] alignment = new int[pairs.length][2];
		for (int i = 0; i != pairs.length; i++) {
			String[] parts = pairs[i].split("-");
			alignment[i][0] = Integer.parseInt(parts[0]);
			alignment[i][1] = Integer.parseInt(parts[1]);
		}
		return alignment;
	}*/
}




