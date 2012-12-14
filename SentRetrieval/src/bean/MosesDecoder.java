package bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * 调用MosesDecoder实现翻译功能
 * @author LiuChunyang
 *
 */
public class MosesDecoder {
	Runtime rt;
	Process proc;
	BufferedReader errorReader;
	BufferedReader resultReader;
	PrintWriter cmdWriter;
	static public String info = "";
	
	MosesDecoder() {
		try{
			rt = Runtime.getRuntime();
			//String runCmd = "/home/locoyou/mosesdecoder/bin/moses -f /home/locoyou/mosesdecoder/sample-models/phrase-model/moses.ini";
			String runCmd = ConfiFile.Moses_bin + " -f " +ConfiFile.Moses_configure;
			info = info + runCmd;
			//System.out.println(runCmd);
			proc = rt.exec(runCmd);
			errorReader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			resultReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			cmdWriter = new PrintWriter(new OutputStreamWriter(proc.getOutputStream()));
			String line;
			while((line = errorReader.readLine()) != null) {
				info = info +"\n"+ line;
	        	//System.out.println(line);
	        	if(line.startsWith("Created input-output")) break;
	        }
		}
		catch (Exception e) {
			info = info + "wrong";
		}
	}
	
	public String decode(String input) {
		String result = "";
		
		try {			
			cmdWriter.println(input);
			cmdWriter.flush();
			String line;
			while((line = errorReader.readLine()) != null) {
	        	System.out.println(line);
	        	if(line.startsWith("Finished translating")||(line.contains("seconds")&&line.contains("total")&&line.contains("Translation")&&line.contains("took"))) 
	        		break;
	        }
			result = resultReader.readLine();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	public static void main(String[] args) {
		MosesDecoder decoder = new MosesDecoder();
		System.out.println("begin decoder");
		System.out.println("result : " + decoder.decode("das ist ein kleines haus"));		
	}
}
