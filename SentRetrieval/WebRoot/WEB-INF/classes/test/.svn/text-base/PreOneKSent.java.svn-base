package test;

import java.io.IOException;
import java.util.Random;

import org.thunlp.io.TextFileReader;
import org.thunlp.io.TextFileWriter;

import bean.ConfiFile;

public class PreOneKSent {
	public static void main(String[] args){
		PreOneKSent p = new PreOneKSent();
		p.selectSentList(1000);
		//p.deleteBlankSent();
	}
	/**
	 * 在tokenization的英文句子中删掉那些空白句子
	 */
	public void deleteBlankSent(){
		try {
			TextFileReader engReader = new TextFileReader(ConfiFile.SentForTestPath+"engSent.txt");
			//TextFileReader chiReader = new TextFileReader(ConfiFile.SentForTestPath+"chineseSentAfterWs");
			//TextFileWriter chiWriter = new TextFileWriter(ConfiFile.SentForTestPath+"chiSent");
			TextFileWriter engWriter = new TextFileWriter(ConfiFile.SentForTestPath+"newEngSent.txt");
			String eline = engReader.readLine();
			//String cline = chiReader.readLine();
			int index =0;
			while(eline!= null ){
				if(eline.matches("[\\s]*")){
					System.out.println(index+1);
					eline = engReader.readLine();
					//cline = chiReader.readLine();
					continue;
				}
				//chiWriter.writeLine(cline);
				engWriter.writeLine(eline);
				eline = engReader.readLine();
				//cline = chiReader.readLine();
				index ++;
			}
			//chiReader.close();
			engReader.close();
			engWriter.close();
			//chiWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private  Random random = new Random(System.currentTimeMillis());
	
	public synchronized int getRandom( int unit) {
		return random.nextInt(unit);
	}
	/**
	 * 分段随即挑选sentamount个句子
	 * @param sentamount 挑选句子的数量
	 */
	public void selectSentList(int sentamount){
		try {
			TextFileReader chiReader = new TextFileReader(ConfiFile.SentForTestPath+"chineseSentAfterWs");
			TextFileReader engReader = new TextFileReader(ConfiFile.SentForTestPath+"englishSentAfterTok");
			TextFileWriter chiWriter = new TextFileWriter(ConfiFile.SentForTestPath+"selectChiSentAfterWs");
			TextFileWriter engWriter = new TextFileWriter(ConfiFile.SentForTestPath+"selectEngSentAfterTok");
			TextFileWriter chiw = new TextFileWriter(ConfiFile.SentForTestPath+"chinese");
			TextFileWriter engw = new TextFileWriter(ConfiFile.SentForTestPath+"english");
			int unit = 515;
			int randomOffset[] = new int[1000];
			for(int i = 0 ; i < sentamount ; i++){
				randomOffset[i] = getRandom(unit);
			}
			boolean end =false;
			int index = 0;
			for(int i = 0 ; i < 515265 ; i++){
				if(index == 1000)
					end = true;
				String cline = chiReader.readLine();
				String eline = engReader.readLine();
				if(end == true){
					if(cline != null && eline != null)
					{
						chiw.writeLine(cline);
						engw.writeLine(eline);
					}
					continue;
				}
				if(i/unit == index  && i % unit == randomOffset[index]){
					chiWriter.writeLine(i+1 +" _ : "+cline);
					engWriter.writeLine(i+1 +" _ :"+eline);
					index++;
				}else{
					chiw.writeLine(cline);
					engw.writeLine(eline);
				}
			}
			chiReader.close();
			engReader.close();
			chiWriter.close();
			engWriter.close();
			chiw.close();
			engw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
