package bean;

import java.io.IOException;
import java.util.HashMap;

import org.thunlp.io.TextFileReader;
/**
 *@author liuqi
 *2012-2-17上午10:30:19
 *
 */
public  class  ConfiFile{
	
	
	public static String FTMakeIndexSource;
		
	public static String FTSentIndexDir;
	
	public static String SentForTestPath;
	
	public static String CEDictionary ;
	
	public static String ECDictionary ;
	
	public static String WordSegmentModel ;
	
	public static String OrigPhraseTable;
	
	public static String FINAL_PHRASE_TABLE ;
	
	public static String Moses_bin;
	
	public static String Moses_configure;
	
	public static String DictFile;
	
	public static String ChiVCB;
	
	public static String EngVCB;
	
	public static String Chi_Eng;
	
	public static String Eng_Chi;
	
	public final static String CHINESE_PUNCTUATION = "[，|。|、|？|：|；|！|‘|”|“|’|（|）|【|】|《|》|…|──|•|+]+";
	
	public final static String ENGLISH_PUNCTUATION = "[\\p{Punct}]+";
	
	public final static String LUCENE_FILT_CHARACTOR = "[+|-||&|\\\\||!|(|)|{|}|\\[|\\]|^|~|*|?|:|]+";
	
	public static String PHRASE_TABLE_SEPERATOR_REG = " ==> ";
	
	public static String SORTED_PHRASE_TABLE_SEPARATOR =" #><# ";
	
	public static boolean ifinited = false;
	
	public static void init(String path){
		if(ifinited )
			return;
		try{
			TextFileReader tr = new TextFileReader(path);	
			HashMap<String, String> confi = new HashMap<String, String>();
			String line ;
			while((line = tr.readLine())!= null){
				String s[] = line.split("><");
				if(s.length > 1)
					confi.put(s[0],s[1]);
			}
			tr.close();
			ConfiFile.FTMakeIndexSource = confi.get("FTMakeIndexSource");
			ConfiFile.FTSentIndexDir = confi.get("FTSentIndexDir");
			ConfiFile.SentForTestPath = confi.get("SentForTestPath");
			ConfiFile.CEDictionary = confi.get("CEDictionary");
			ConfiFile.ECDictionary = confi.get("ECDictionary");
			ConfiFile.WordSegmentModel = confi.get("WordSegmentModel");
			ConfiFile.OrigPhraseTable = confi.get("OrigPhraseTable");
			ConfiFile.FINAL_PHRASE_TABLE = confi.get("FINAL_PHRASE_TABLE");
			ConfiFile.Moses_bin = confi.get("Moses_bin");
			ConfiFile.Moses_configure = confi.get("Moses_configure");
			ConfiFile.DictFile = confi.get("DictFile");
			ConfiFile.ChiVCB = confi.get("ChiVCB");
			ConfiFile.EngVCB = confi.get("EngVCB");
			ConfiFile.Chi_Eng = confi.get("Chi_Eng");
			ConfiFile.Eng_Chi = confi.get("Eng_Chi");
			System.out.println("print word Segment Model path =>>>>>>>>"+ConfiFile.WordSegmentModel);
			
			ifinited = true;
		}
		catch(IOException e) {
			
		}

	}
	public static void main(String[] args){
		init("E:\\MyEclipseProjects\\SentRetrieval\\WebRoot\\configFile.conf");
	}
}
