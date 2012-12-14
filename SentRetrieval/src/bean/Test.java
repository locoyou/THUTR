package bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.regex.Pattern;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.util.Version;

public class Test {
	/*public static void main(String[] args){
		String sent = "It claims that his decision not to impose punitive tariffs on Korean three-door side-by-side fridges would have direct and very significant consequences for the European industry.";
		String word = "three-door side-by-side";
		//String word ="claims";
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
		QueryParser parser = new QueryParser(Version.LUCENE_31, "field",analyzer);
		Query query = null;
		try {
			query = parser.parse(word);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		QueryScorer scorer = new QueryScorer(query);
		Highlighter highlighter = new Highlighter(scorer);
		TokenStream tokenStream = 
			analyzer.tokenStream("field",new StringReader(sent));
		try {
			System.out.println(highlighter.getBestFragment(tokenStream, sent));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTokenOffsetsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	//public static Pattern EngPuncPat = Pattern.compile("[,.?:&;’-]*");
	
	public static void main(String[] args) throws IOException{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String str= "";
		System.out.print("Query:");
		while((str = stdin.readLine())!= null){
			if(str.equals("!q"))
				break;
			//if(Pattern.matches("[\\p{Punct}]+",str))
			if(str.matches(ConfiFile.LUCENE_FILT_CHARACTOR))
			//if(str.matches("\\p{Punct}*"))
			//if(str.matches("[，|。|、|？|：|；|！|‘|”|“|’|（|）|【|】|《|》|…|──|•|+]+"))
				System.out.println(str+" yes");
			else
				System.out.println(str+" no");
			System.out.print("Query:");
		}
	}
}
