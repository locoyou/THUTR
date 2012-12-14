package org.thunlp.mt.wa.phrase;

import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.ConfiFile;

public class PhraseTable {
	
	private Map<String, Map<String, int[][]>> phraseTable = new HashMap<String, Map<String, int[][]>>();
	static Gson GSON_BUILDER = (new GsonBuilder()).disableHtmlEscaping().create();
	public void addEntry(String source, String target, int[][] alignmentInfo) {
		if (phraseTable.containsKey(source))
			phraseTable.get(source).put(target, alignmentInfo);
		else {
			Map<String, int[][]> map = new HashMap<String, int[][]>();
			map.put(target, alignmentInfo);
			phraseTable.put(source, map);
		}
	}
	
	public int[][] getAlignmentInfo(String source, String target) {
		if (phraseTable.containsKey(source))
			return phraseTable.get(source).get(target);
		else
			return null;
	}
	
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
	
	public static PhraseTable build(File file) throws IOException {
		PhraseTable phraseTable = new PhraseTable();
		
		BufferedReader reader;
		if (file != null)
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		else {
			InputStream is = phraseTable.getClass().getClassLoader()
					.getResourceAsStream(ConfiFile.OrigPhraseTable);
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		}
		
		String str;
		while((str = reader.readLine()) != null) {
			String[] parts = str.split(ConfiFile.PHRASE_TABLE_SEPERATOR_REG);
			BilingualPhraseWithAlignmentInfo phrase = 
				GSON_BUILDER.fromJson(parts[0], BilingualPhraseWithAlignmentInfo.class);
			int[][] alignment = getAlignmentInfo(phrase.alignmentInfo);
			phraseTable.addEntry(phrase.source, phrase.target, alignment);
		}
		reader.close();
		return phraseTable;
	}
	
	private static int[][] getAlignmentInfo(String alignmentStr) {
		String[] pairs = alignmentStr.split(" ");
		int[][] alignment = new int[pairs.length][2];
		for (int i = 0; i != pairs.length; i++) {
			String[] parts = pairs[i].split("-");
			alignment[i][0] = Integer.parseInt(parts[0]);
			alignment[i][1] = Integer.parseInt(parts[1]);
		}
		return alignment;
	}
}
