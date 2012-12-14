package bean;

import java.util.ArrayList;
import java.util.Map;
import tools.WordProPair;

/**
 * 
 * 实现解码器部分
 * 通过查词典和短语表完成翻译
 * @author LiuChunyang
 * 
 */

public class Decoder {
	Map<String, ArrayList<WordProPair>> dictionary;
	Map<String, ArrayList<WordProPair>> phraseTable;
	ArrayList<WordProPair> outputList;
	int maxLen;
	int maxStack;
	
	Decoder() {
		maxStack = 100;
		maxLen = 2;
		//dictionary = ChiConvertToEngBean.CEDictionary;
		//phraseTable = ChiConvertToEngBean.PhraseDictionary;
	}
	
	public String decode(String input) {
		double alpha = 1.05;
		String []words = input.split(" ");
		Stack []stacks = new Stack[words.length+1];
		stacks[0] = new Stack(maxStack);
		stacks[0].add(new WordProPair("",1));
		for(int i = 1; i <= words.length; i++) {
			stacks[i] = new Stack(maxStack);
			String word = "";
			boolean traned = false;
			for(int j = 1; j <= maxLen; j++) {
				if(i - j < 0) break;
				ArrayList<WordProPair> list = stacks[i-j].getList();
				if(j == 1) word = words[i - j];
				else word = words[i - j] + " " + word;
				ArrayList<WordProPair> wordList = new ArrayList<WordProPair>();
				if(j == 1 && dictionary.containsKey(word)) {
					traned = true;
					wordList = dictionary.get(word);
				}
				if(j > 1 && phraseTable.containsKey(word)) {
					traned = true;
					wordList = phraseTable.get(word);
				}
				if(wordList.size() != 0) {
					for(WordProPair text : list) {													
						for(WordProPair w : wordList) {
							//System.out.println(i+" "+text.eng+" "+w.eng);
							stacks[i].add(new WordProPair(text.CorreWord+" "+w.CorreWord, text.Prob*w.Prob*alpha));
						}					
					}
				}
			}
			if(!traned) {
				word = words[i-1];
				ArrayList<WordProPair> list = stacks[i-1].getList();
				for(WordProPair text : list)
					stacks[i].add(new WordProPair(text.CorreWord+" "+word, text.Prob*0.5*alpha));
			}
		}
		/*for(WordProPair t : stacks[words.length].getList()) {
			System.out.println(t.CorreWord + " " + t.Prob);
		}*/
		return stacks[words.length].getList().get(0).CorreWord;
	}
	/*public String decode(String input) {
		outputList = new ArrayList<Text>();
		String []words = input.split(" ");
		maxLen = configuration.getMaxLen();
		decode("", words, 0, 1.0, 0);

		return "";
	}
	
	private void decode(String tmpOutput, String []words, int index, double probability, int num) {
		if(index >= words.length) {
			Text output = new Text(tmpOutput, probability*Math.pow(1.2, num));
			System.out.println(tmpOutput + " " + probability*Math.pow(1.2, num));
			outputList.add(output);
		}
		else {
			String word = words[index];
			boolean traned = false;
			if(dictionary.containsKey(word)) {
				traned = true;
				ArrayList<Text> wordList = dictionary.get(word);
				for(Text w : wordList) {
					decode(tmpOutput+" "+ w.eng, words, index+1, probability*w.probability, num+1);
				}
			}
			for(int i = 1; i < maxLen && index + i < words.length; i++) {
				word = word + " " + words[index+i];
				if(phraseTable.containsKey(word)) {
					traned = true;
					ArrayList<Text> wordList = phraseTable.get(word);
					for(Text w : wordList) {
						decode(tmpOutput+" "+ w.eng, words, index+i+1, probability*w.probability, num+1);
					}
				}
			}
			if(!traned) {
				decode(tmpOutput+" "+words[index], words, index+1, probability*0.5, num+1);
			}
		}
		
	}*/
}