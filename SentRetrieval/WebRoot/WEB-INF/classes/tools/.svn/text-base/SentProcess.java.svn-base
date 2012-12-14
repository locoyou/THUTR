package tools;

import java.io.IOException;

import org.thunlp.io.TextFileReader;

public class SentProcess {
	public void pro(){
		try {
			TextFileReader reader = new TextFileReader("chineseSentAfterWs");
//			TextFileWriter writer = new 
			String line = "";
			while((line = reader.readLine()) != null){
				if(line.endsWith(" ")){
					System.out.println();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		
	}
}
