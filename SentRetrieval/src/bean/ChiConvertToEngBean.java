package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.thunlp.io.TextFileReader;

import word.segment.PathLabel;

public class ChiConvertToEngBean {
	private static boolean isInited = false;
	private static MosesDecoder decoder;
	private static HashMap<String, ArrayList<String>> dict;

	public void init(String path){
		if(!isInited){
			ConfiFile.init(path);
			PathLabel.init(path);
			SortByCount.integrity = new Integrity();
			isInited = true;
			decoder = new MosesDecoder();
			try{
				TextFileReader tr = new TextFileReader(ConfiFile.DictFile);
				dict = new HashMap<String, ArrayList<String>>();
				String line;
				while((line = tr.readLine())!= null){
					String s[] = line.split(" \\|\\|\\| ");
					if(dict.containsKey(s[0])) {
						dict.get(s[0]).add(s[1]);
					}
					else {
						ArrayList<String> list = new ArrayList<String>();
						list.add(s[1]);
						dict.put(s[0], list);
					}
				}
			}
			catch(IOException e) {
				
			}
		}
	}
	public ChiConvertToEngBean(){
	}
	
	public String convertToTargetUseDict(String sourceSent) {
		String str = "";
		String[] words = sourceSent.split(" ");
		for(int i = 0; i < words.length; i++) {
			if(dict.containsKey(words[i])) {
				str = str + " AND (";
				for(String trans: dict.get(words[i])) {
					str = str + trans + " OR ";
				}
				str = str.substring(0, str.length()-4);
				str = str + ")";
			}
		}
		if(str.length() > 5) 
			str = str.substring(5, str.length());
		return str;
	}
	
	public String convertToTargetSentUsePhrase(String sourceSent){
		String str = decoder.decode(sourceSent);
		String word = convertToTargetUseDict(sourceSent);
		System.out.println(str);
		return "("+str+") OR ("+word+")";
		
	}
	
	public String convertToTargetSent(String sourceSent){
		String str = decoder.decode(sourceSent);
		System.out.println(str);
		return str;
	}

	
	public void testinit() {
		try{
			TextFileReader tr = new TextFileReader("E://test.txt");
			dict = new HashMap<String, ArrayList<String>>();
			String line;
			while((line = tr.readLine())!= null){
				String s[] = line.split(" \\|\\|\\| ");
				if(dict.containsKey(s[0])) {
					dict.get(s[0]).add(s[1]);
				}
				else {
					ArrayList<String> list = new ArrayList<String>();
					list.add(s[1]);
					dict.put(s[0], list);
				}
			}
			Iterator iter = dict.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String)entry.getKey();
			}
		}
		catch(IOException e) {
			
		}
	}
	
	public static void main(String[] args) {
		ChiConvertToEngBean chi = new ChiConvertToEngBean();
		chi.testinit();
		System.out.println(chi.convertToTargetUseDict("葡萄 样的"));
	}
	
}
