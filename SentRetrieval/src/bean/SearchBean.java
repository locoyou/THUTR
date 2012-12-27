package bean;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchBean {
	public SearchBean(){
	}
	
	public String transformSolrMetacharactor(String input){
	    StringBuffer sb = new StringBuffer();
	    String regex = "[+\\-&|!(){}\\[\\]^\"~*?:(\\)]";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(input);
	    while(matcher.find()){
	        matcher.appendReplacement(sb, "\\\\"+matcher.group());
	    }
	    matcher.appendTail(sb);
	    return sb.toString();
	}
	
	public ArrayList<Integer> getIndexResult(String searchWord) {
		try{
			//SimpleAnalyzer sim = new SimpleAnalyzer();
			searchWord = transformSolrMetacharactor(searchWord);
			Analyzer analyzer = new EnglishAnalyzer(Version.LUCENE_36);
			
			
			//Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
			//Directory fsDir = FSDirectory.open(new File(ConfiFile.FTChinaIndexDir));
			
			Directory fsDir = FSDirectory.open(new File(ConfiFile.FTSentIndexDir));
			IndexReader ireader = IndexReader.open(fsDir);
			IndexSearcher searcher = new IndexSearcher(ireader);
			QueryParser parser = new QueryParser(Version.LUCENE_36, "content",analyzer);
			Query query = null;
			try {
				query = parser.parse(searchWord);
			} catch (ParseException e) {
				
				e.printStackTrace();
				return null;
			}
			ArrayList<Integer> result = new ArrayList<Integer>();
			if(query != null){
				ScoreDoc[] hits  = searcher.search(query,null,50).scoreDocs;
				//System.out.println("hit length" + hits.length);
				for(int i = 0 ; i < hits.length; i++){
					Document hitDoc = searcher.doc(hits[i].doc);
					String index = hitDoc.get("index");
					if(i < 10){
						System.out.println(index+" " +hitDoc.get("content"));
					}
					if(index != null)
						result.add(new Integer(index));
				}
			}
			searcher.close();
			fsDir.close();
			return result;
		}catch(IOException e){
			return null;
		}
		
	}
	public ArrayList<SearchResult> getEntireResult(String searchWord){
		try{
			searchWord = transformSolrMetacharactor(searchWord);
			HashMap<String, Integer> resultSet = new HashMap<String, Integer>();
			Analyzer analyzer = new EnglishAnalyzer(Version.LUCENE_36);
			Directory fsDir = FSDirectory.open(new File(ConfiFile.FTSentIndexDir));
			IndexReader ireader = IndexReader.open(fsDir);
			IndexSearcher searcher = new IndexSearcher(ireader);
			QueryParser parser = new QueryParser(Version.LUCENE_36, "content",analyzer);
			Query query = null;
			try {
				query = parser.parse(searchWord);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			ArrayList<SearchResult> result = new ArrayList<SearchResult>();
			if(query != null){
				ScoreDoc[] hits  = searcher.search(query,null,500).scoreDocs;
				//System.out.println("hit length" + hits.length);
				for(int i = 0 ; i < hits.length; i++){
					Document hitDoc = searcher.doc(hits[i].doc);
					String content = hitDoc.get("content");
					int index = new Integer(hitDoc.get("index")).intValue();
					if(hitDoc.get("content") != null) {
						SearchResult sr = new SearchResult(getHighlight(content,searchWord),index, hits[i].score);
						if(!resultSet.containsKey(sr.Sent)) {
							resultSet.put(sr.Sent, 1);
							result.add(new SearchResult(getHighlight(content,searchWord),index, hits[i].score));
						}
					}
				}
			}
			searcher.close();
			fsDir.close();
			return result;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	public String getHighlight(String sent,String word){
		EnglishAnalyzer analyzer = new EnglishAnalyzer(Version.LUCENE_36);
		
		//Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
		QueryParser parser = new QueryParser(Version.LUCENE_36, "field",analyzer);
		Query query = null;
		try {
			query = parser.parse(word);
			QueryScorer scorer = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(scorer);
			TokenStream tokenStream = 
				analyzer.tokenStream("field",new StringReader(sent));
			highlighter.setTextFragmenter(new SimpleFragmenter(1000));
			return highlighter.getBestFragment(tokenStream, sent);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public static void main(String[] args){
		SearchBean b = new SearchBean();
		ArrayList<SearchResult> re = b.getEntireResult("india");
		for(int i = 0 ; i < 10 ; i++)
			System.out.println(re.get(i));
	}
}
