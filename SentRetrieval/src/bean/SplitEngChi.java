package bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.thunlp.io.TextFileWriter;
import org.thunlp.language.chinese.LangUtils;

/**
 *@author liuqi
 *2012-2-21上午10:54:16
 *
 */
public class SplitEngChi {
	public static void main(String[] args) throws IOException{
		
		try {
			//BufferedReader br = new BufferedReader(new FileReader(ConfiFile.FTChinaFilePath+"FTChinaALLAlignedSentences.txt"));
			BufferedReader br = new BufferedReader(new FileReader("FTChinaALLAlignedSentences.txt"));
			String cline= new String();
			String eline= new String();
			int index = 0;
			/*TextFileWriter wchi= new TextFileWriter(ConfiFile.FTChinaFilePath+"ChiSent.txt");
			TextFileWriter weng= new TextFileWriter(ConfiFile.FTChinaFilePath+"EngSent.txt");*/
			TextFileWriter wchi= new TextFileWriter("ChiSent.txt");
			TextFileWriter weng= new TextFileWriter("EngSent.txt");
			while(true){
				String line = br.readLine();
				if(line == null)
					break;
				if(index % 2 == 0){
					cline = LanguageUtil.removeEngInChi(line);
				}else{
					eline = LangUtils.mapChineseMarksToAnsi(line);
					eline = LanguageUtil.removeChiInEng(eline);
					if(!eline.matches("[\\s]*") && !cline.matches("[\\s]*")){
						wchi.writeLine(cline);
						weng.writeLine(eline);
					}
					cline = eline = "";
				}
				if(index % 10000 == 0)
					System.out.println(index);
				index++;
			}
			wchi.close();
			weng.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
