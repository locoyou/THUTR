package word.segment;

import java.io.IOException;

import org.thunlp.io.TextFileReader;
import org.thunlp.io.TextFileWriter;

public class ChineseWordSegment {
	public void pro(String chifilePath){
		PathLabel pl = new PathLabel();
		try {
			TextFileReader freader = new TextFileReader(chifilePath);
			TextFileWriter fwriter = new TextFileWriter("chifileAfWs.txt");
			String line = "";
			while((line = freader.readLine()) != null){
				pl.setSentence(line);
				String newLine = pl.tokenization();
				fwriter.writeLine(newLine);
			}
			freader.close();
			fwriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleLab(){
		try {
			TextFileReader freader = new TextFileReader("chifileAfWs.txt");
			TextFileWriter fwriter = new TextFileWriter("newchifileAfWs.txt");
			String line = "";
			while((line = freader.readLine()) != null){
				String[] words = line.split(" ");
				String newLine ="";
				for(String word :words){
					int index = word.indexOf("/");
					if(index >0)
						newLine += word.substring(0, index)+" ";
				}
				fwriter.writeLine(newLine);
			}
			freader.close();
			fwriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String [] args){
		 ChineseWordSegment s = new  ChineseWordSegment();
		 //s.pro("ChiSent.txt");
		 s.deleLab();
	}
}
