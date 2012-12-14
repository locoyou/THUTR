package bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author locoyou
 * 对lucene检索结果进行高亮重置并按照统计数量重新排序
 */

public class SortByCount {
	HashMap<String, ArrayList<ScanHightLighter>> map;
	ArrayList<Map.Entry<String, ArrayList<ScanHightLighter>>> sorted;
	static Integrity integrity;
	String chiSent;
	
	public SortByCount(ArrayList<SearchResult> result, final String chi) {
		chiSent = chi;
		map = new HashMap<String, ArrayList<ScanHightLighter>>();
		
		for(int i = 0; i < result.size(); i++) {
			
			ScanHightLighter sh = new ScanHightLighter(result.get(i));
			if(sh.hit) {
				if(map.containsKey(sh.highlight)) {
					map.get(sh.highlight).add(sh);
				}
				else {
					ArrayList<ScanHightLighter> list = new ArrayList<ScanHightLighter>();
					list.add(sh);
					map.put(sh.highlight, list);
				}
			}
		}
		sorted = new ArrayList<Map.Entry<String, ArrayList<ScanHightLighter>>>(map.entrySet());
		Collections.sort(sorted, new Comparator<Map.Entry<String, ArrayList<ScanHightLighter>>>() {   
		    public int compare(Map.Entry<String, ArrayList<ScanHightLighter>> o1, Map.Entry<String, ArrayList<ScanHightLighter>> o2) {    
		    	ArrayList<ScanHightLighter> l1 = o1.getValue();
		    	ArrayList<ScanHightLighter> l2 = o2.getValue();
		    	double score1 = 0, score2 = 0;
		    	for(int i = 0; i < l1.size(); i++)
		    		score1 += l1.get(i).score;
		    	score1 = score1 / l1.size();
		    	score1 *= integrity.getProb(chiSent, o1.getKey());
		    	for(int i = 0; i < l2.size(); i++)
		    		score2 += l2.get(i).score;
		    	score2 = score2 / l2.size();
		    	score2 *= integrity.getProb(chiSent, o2.getKey());
		    	double v1 = score1 * o1.getValue().size()*Math.sqrt((double)(o1.getKey().split(" ").length));
		    	double v2 = score2 * o2.getValue().size()*Math.sqrt((double)(o2.getKey().split(" ").length));
		        if(v2 >  v1)
		        	return 1;
		        else if(v2 < v1)
		        	return -1;
		        else
		        	return 0;
		    }
		}); 
	}
	
	public ArrayList<Map.Entry<String, ArrayList<ScanHightLighter>>> getSorted() {
		return sorted;
	}
}

