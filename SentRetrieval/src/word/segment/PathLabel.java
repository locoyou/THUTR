package word.segment;

import java.util.*;

import bean.ConfiFile;

class LexNode {
	// features of each node, including position, word, word-tag and 7 auxiliary word-features
	int pos =  0;
	String word;
	String tag;
	String[] features;
	
	public LexNode(String word, String label){
		this.word = word;
		String[] tags = label.split("/");
		this.tag = tags[1];
		this.pos = Integer.parseInt(tags[0]);
		this.features = new String[7];
	}
	
	public LexNode(String word){
		this.word = word;
		this.tag = new String();
		this.pos = 0;
		this.features = new String[7];
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
	
	public void setPos(int pos){
		this.pos = pos;
	}
	// for each LexNode, obtain seven features in context
	public void setFeatures(String sent){
		int index = sent.indexOf(this.word);
		String leftChar = index - 1 >= 0 ? Character.toString(sent.charAt(index-1)) : "#";
		String rightChar = index + 1 < sent.length() ? Character.toString(sent.charAt(index+1)) : "#";
		String left2Char = index - 2 >= 0 ? Character.toString(sent.charAt(index-2)) : "#";
		String right2Char = index + 2 < sent.length() ? Character.toString(sent.charAt(index+2)) : "#";
		
		features[0] = this.word + " 1";
		features[1] = leftChar + " 2";
		features[2] = rightChar + " 3";
		features[3] = leftChar + this.word + " 1";
		features[4] = this.word + rightChar + " 2";
		features[5] = left2Char + leftChar + " 3";
		features[6] = rightChar + right2Char + " 4";
	}
}

public class PathLabel {
	
	static LexiconModel lexicon;
	String sentence;
	List<LexNode> wordNodes;
	
	public static void init(String path){ 
		ConfiFile.init(path);
		lexicon = new LexiconModel();
	}
	
	public PathLabel(String sent){
		this.sentence = sent;
		this.wordNodes = new ArrayList<LexNode>();
		String[] words = sent.split("");
		for(int i = 1;i < words.length;i++){
			LexNode node = new LexNode(words[i]);
			node.setFeatures(this.sentence);
			this.wordNodes.add(node);
		}
		//lexicon = new LexiconModel();
	}

	public PathLabel(){
		this.sentence = new String();
		this.wordNodes = new ArrayList<LexNode>();
		//lexicon = new LexiconModel();
	}
	
	public /*List<Word>*/String tokenization(){
		
		int x = sentence.length(),
			y = lexicon.labelSize;
		int[][] weight = new int[x][y];		// for each node, weight records the weight of i-th node with j-th label
		int[][] value = new int[x][y];		// for first i node, value records the weights of first i-th nodes with a j-th label ending
		int[][] path = new int[x][y];		// for each node, path records the i-th node with j-th label's previous index of label
		
//		for(LexNode node : wordNodes)
//			node.setFeatures(sentence);
		int index_x, index_y;
		int sum = 0;
		// initialize weight matrix for dynamic programming
		for(int i = 0;i < x;i++)
			for(int j = 0;j < y;j++){
				sum = 0;
				for(int k = 0;k < 7;k++){
					index_x = 0;
					if(lexicon.indexFeatureDic.containsKey(wordNodes.get(i).features[k])){
						index_x = lexicon.indexFeatureDic.get(wordNodes.get(i).features[k]);
						index_y = j;
						sum += lexicon.fealabWeight[index_x][index_y];						
					}	else 
						sum += 0;
				}
				weight[i][j] = sum;
			}
				
		for(int j = 0;j < y;j++){
			value[0][j] = weight[0][j];
			path[0][j] = -1;
		}
			
		for(int i = 1;i < x;i++)
			for(int j = 0;j < y;j++){
				value[i][j] = Integer.MIN_VALUE;
				path[i][j] = -1;
				for(int k = 0;k < y;k++){
					if(value[i-1][k] + lexicon.labelWeight[k][j] + weight[i][j] > value[i][j]){
						value[i][j] = value[i-1][k] + lexicon.labelWeight[k][j] + weight[i][j];
						path[i][j] = k;
					}
				}
			}
		
		int maxValue = Integer.MIN_VALUE;
		int indexOfLabel = -1;
		int[] printPath = new int[x];
		for(int j = 0;j < y;j++)
			if(value[x-1][j] > maxValue){
				maxValue = value[x-1][j];
				indexOfLabel = j;
			}
		for(int j = x-1;j >= 0;j--){
			printPath[j] = indexOfLabel;
			indexOfLabel = path[j][indexOfLabel];
		}
		
		for(int i = 0;i < x;i++){
			String tag = lexicon.labelDic.get(printPath[i]);
			String[] tags = tag.split("/");
			wordNodes.get(i).setPos(Integer.parseInt(tags[0]));
			wordNodes.get(i).setTag(tags[1]);
		}
		
//		List<Word> wordlex = new ArrayList<Word>();
		StringBuilder wordBuilder = new StringBuilder();
//		WordTag tag;
		for(int i = 0;i < x;i++){
			if(wordNodes.get(i).pos == 0){
				wordBuilder.append(wordNodes.get(i).word);
			} else if(wordNodes.get(i).pos == 1){
				wordBuilder.append(wordNodes.get(i).word);
			} else if(wordNodes.get(i).pos == 2){
				wordBuilder.append(wordNodes.get(i).word);
				wordBuilder.append("/" + wordNodes.get(i).tag + " ");
//				tag = WordTag.tagMapping(wordNodes.get(i).tag);
//				wordlex.add(new Word(wordBuilder.toString(), tag));
			} else if(wordNodes.get(i).pos == 3){
//				wordBuilder = new StringBuilder();
				wordBuilder.append(wordNodes.get(i).word);
				wordBuilder.append("/" + wordNodes.get(i).tag + " ");
//				tag = WordTag.tagMapping(wordNodes.get(i).tag);
//				wordlex.add(new Word(wordBuilder.toString(), tag));
			}
		}
		return wordBuilder.toString();
//		return wordlex;
	}
	
	public void setSentence(String string){
		this.sentence = string;
		wordNodes.clear();
		String[] words = string.split("");
		for(int i = 1;i < words.length;i++){
			LexNode node = new LexNode(words[i]);
			node.setFeatures(this.sentence);
			this.wordNodes.add(node);
		}
	}
		
	public static void main(String[] args){
	//	ConfiFile.init("D://Workspaces/SentRetrieval/configFile.conf");
		init("D://Workspaces/SentRetrieval/configFile.conf");
		System.out.println(ConfiFile.WordSegmentModel);
		PathLabel pathLabel = new PathLabel();
		pathLabel.setSentence("哈哈哈这是第二句");
		String text = pathLabel.tokenization();
		System.out.println(text);
//		try{
//			BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Keira\\Workspaces\\MyEclipse for Spring 8.6\\Tokenization\\maosongSun.txt"));
//			String tmp = new String();
//			HashSet<String> wordBag = new HashSet<String>();
//			while((tmp = in.readLine()) != null){
//				tmp = tmp.replaceAll("\\d", "");
//				tmp = tmp.replaceAll("[@#/$-*&^%<>]", "");
//				pathLabel.setSentence(tmp);
//				List<Word> words = pathLabel.tokenization();
//				for(Word word : words){
//					if(WordTag.isNoun(word.getTag()) && !word.getWord().equals(" "))
//						wordBag.add(word.getWord());
//				}
//			}
//			in.close();
////			text = text.replaceAll("http://.*[A-Za-z0-9]", "");
////			text = text.replaceAll("//@.*:", "");
////			text = text.replaceAll("[\\+\\.\\*？：/\\[\\]‘’“”;；$^&\\(\\)@#~%^{}【】，。,.\\?<>《》:、\'\"]", "");
//			BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Keira\\Workspaces\\MyEclipse for Spring 8.6\\Tokenization\\logout.txt"));
////			String text = "最近想做一点微博的研究。所以先试一下水，体会体会。如鱼饮水，冷暖自知嘛。" +
////					"正如老人家说，要想知道梨子的滋味，一定要亲口尝一尝......尝出甜头后，也许就常驻了，“水调歌头”.......";
//			for(String word : wordBag)
//				out.write(word + "\n");
//			out.close();
//		} catch(Exception e){
//			e.printStackTrace();
//		}
	}
}















