package tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
/*import java.util.HashMap;
import java.util.List;
import java.util.Map;*/

import org.thunlp.io.TextFileReader;
import org.thunlp.io.TextFileWriter;

import bean.ConfiFile;

public class ChiEngDicGenerator {
	/*public static Pattern Eng*/
	public static void main(String[] args){
		ChiEngDicGenerator ceg = new ChiEngDicGenerator();
		ceg.genDict("chinese-english.t3.final");
	}
	public void genDict(String filename){
		int lastchiindex = 0;
		//Map<String,List<WordProPair>> Dictionary = new HashMap<String,List<WordProPair>>();
		try {
			TextFileReader chiVobReader = new TextFileReader("chinese.vcb");
			String chiLine = chiVobReader.readLine();
			ArrayList<String> chiVobList = new ArrayList<String>();
			ArrayList<String> engVobList = new ArrayList<String>();
			while(chiLine != null){
				String [] a = chiLine.split(" ");
				chiVobList.add(a[1]);
				chiLine = chiVobReader.readLine();
			}
			chiVobReader.close();
			// 增加 中文和英文标点过滤的模块！！
			TextFileReader engVobReader = new TextFileReader("english.vcb");
			String engLine = engVobReader.readLine();
			while(engLine != null){
				String [] b = engLine.split(" ");
				engVobList.add(b[1]);
				engLine = engVobReader.readLine();
			}
			engVobReader.close();
			System.out.println("chi eng vob read end chilist size "+ chiVobList.size() +" englist size "+ engVobList.size());
			System.out.println(chiVobList.get(chiVobList.size()-1));
			System.out.println(engVobList.get(engVobList.size()-1));
			/*int a = 0 ;
			if (a == 0)
				return;*/
			TextFileReader reader = new TextFileReader(filename);
			String line = reader.readLine();
			ArrayList<WordProPair> corrWordList = new ArrayList<WordProPair>() ;
			WordProComparator c = new WordProComparator();
			TextFileWriter writer = new TextFileWriter("chinese_english.dict",true);
			//int j = 0;
			while(line != null){
				String[] numbers = line.split(" ");
				/*j++;
				System.out.println(numbers[1]+" "+numbers[2]);*/
				/*if(j > 20)
					break*/;
				int chiindex = new Integer(numbers[0]).intValue();
				int engindex = new Integer(numbers[1]).intValue();
				if(chiindex <2 || engindex<2){
					line = reader.readLine();
					continue;
				}
				if(lastchiindex != chiindex){
					//System.out.println("new word "+chiindex);
					Collections.sort(corrWordList,c);
					if(corrWordList.size() < 10){
						//Dictionary.put(chiVobList.get(lastchiindex), corrWordList);
						for(int i = 0 ; i < corrWordList.size() ; i++)
							writer.writeLine(chiVobList.get(lastchiindex-2)+" "+corrWordList.get(i).toString());
					}else{
						//ArrayList<WordProPair> result = new ArrayList<WordProPair>();
						for(int i = 0 ;i< 10 ;i ++)
							writer.writeLine(chiVobList.get(lastchiindex-2)+" "+corrWordList.get(i).toString());
							//result.add(corrWordList.get(i));
					}
					lastchiindex  = chiindex;
					corrWordList.clear();
				}
				
				
				String chivob = chiVobList.get(chiindex-2);
				if(chivob.matches(ConfiFile.CHINESE_PUNCTUATION) || chivob.matches(ConfiFile.ENGLISH_PUNCTUATION)){
					line = reader.readLine();
					continue;
				}
				String engvob = engVobList.get(engindex-2);
				if(engvob.matches(ConfiFile.ENGLISH_PUNCTUATION)){
					line = reader.readLine();
					continue;
				}

				double pro = new Double(numbers[2]).doubleValue();
				corrWordList.add(new WordProPair(engvob,pro));
				//System.out.println("add "+chiVobList.get(chiindex-2) +" "+engVobList.get(engindex-2) +" "+ pro);
				line = reader.readLine();
				/*if(chiindex == 4)
					return;*/
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
