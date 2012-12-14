package bean;

import java.io.IOException;
import java.util.HashMap;

import org.thunlp.io.TextFileReader;

/**
 * 
 * @author locoyou
 *	使用IBM model 1 计算两个句子之间的信息完整程度
 */
class Integrity {
	HashMap<String, Integer> chi;
	HashMap<String, Integer> eng;
	HashMap<String, Double> chi_eng;
	HashMap<String, Double> eng_chi;
	 
	public Integrity() {
		try{
			chi = new HashMap<String, Integer>();
			eng = new HashMap<String, Integer>();
			chi_eng = new HashMap<String, Double>();
			eng_chi = new HashMap<String, Double>();
			String line;
			//TextFileReader tr = new TextFileReader("D:\\MyEclipse-8.6\\Workspace\\SentRetrieval\\FBIS.chi.vcb");
			TextFileReader tr = new TextFileReader(ConfiFile.ChiVCB);
			while((line = tr.readLine()) != null) {
				String s[] = line.split(" ");
				chi.put(s[1], Integer.valueOf(s[0]));
			}
			tr.close();
			//tr = new TextFileReader("D:\\MyEclipse-8.6\\Workspace\\SentRetrieval\\FBIS.eng.vcb");
			tr = new TextFileReader(ConfiFile.EngVCB);
			while((line = tr.readLine()) != null) {
				String s[] = line.split(" ");
				eng.put(s[1], Integer.valueOf(s[0]));
			}
			tr.close();
			//tr = new TextFileReader("D:\\MyEclipse-8.6\\Workspace\\SentRetrieval\\FBIS.chi-eng.t3.final");
			tr = new TextFileReader(ConfiFile.Chi_Eng);
			while((line = tr.readLine()) != null) {
				String s[] = line.split(" ");
				chi_eng.put(s[1]+"-"+s[0], Double.valueOf(s[2]));
			}
			tr.close();
			//tr = new TextFileReader("D:\\MyEclipse-8.6\\Workspace\\SentRetrieval\\FBIS.eng-chi.t3.final");
			tr = new TextFileReader(ConfiFile.Eng_Chi);
			while((line = tr.readLine()) != null) {
				String s[] = line.split(" ");
				eng_chi.put(s[1]+"-"+s[0], Double.valueOf(s[2]));
			}
			tr.close();
		}
		catch (IOException e) {
			
		}
	}
	
	public double getProb(String chiSent, String engSent) {
		double result = 1;
		String[] chiWords = chiSent.split(" ");
		String[] engWords = engSent.split(" ");	
 		int l = engWords.length;
		int m = chiWords.length;
		int[] chiIndex = new int[m];
		int[] engIndex = new int[l];
		for(int j = 0; j < m; j++) {
			if(chi.containsKey(chiWords[j]))
				chiIndex[j] = chi.get(chiWords[j]);
			else 
				chiIndex[j] = 0;
		}
		for(int i = 0; i < l; i++) {
			if(eng.containsKey(engWords[i]))
				engIndex[i] = eng.get(engWords[i]);
			else 
				engIndex[i] = 0;
		}
		for(int j = 0; j < m; j++) {
			double t = 0;
			if(chi_eng.containsKey(chiIndex[j]+"-0"))
				t += chi_eng.get(chiIndex[j]+"-0");
			for(int i = 0; i < l; i++) {
				if(engWords[i].length() < 2 && !engWords[i].equalsIgnoreCase("i"))
					continue;
				if(chi_eng.containsKey(chiIndex[j]+"-"+engIndex[i]))
					t = t + chi_eng.get(chiIndex[j]+"-"+engIndex[i]);
			}
			result = result*t;
		}
		result = result / Math.pow(l+1, m);
		
		for(int i = 0; i < l; i++) {
			double t = 0;
			if(engWords[i].length() < 2 && !engWords[i].equalsIgnoreCase("i"))
				continue;
			if(eng_chi.containsKey(engIndex[i]+"-0")) 
				t += eng_chi.get(engIndex[i]+"-0");
			for(int j = 0; j < m; j++) {
				if(eng_chi.containsKey(engIndex[i]+"-"+chiIndex[j]))
					t = t + eng_chi.get(engIndex[i]+"-"+chiIndex[j]);
			}
			result = result*t;
		}
		result = result / Math.pow(m+1, l);
		return Math.pow(result, 0.5);
	}
	
	public static void main(String[] args) {
		Integrity integrity = new Integrity();
		System.out.println(integrity.getProb("中国 经济", "china ' s economy"));
		System.out.println(integrity.getProb("中国 经济", "the chinese economy , while [ china"));
	}
}