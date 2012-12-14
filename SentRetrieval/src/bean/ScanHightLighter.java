package bean;

/**
 * 
 * @author locoyou
 * 扫描Lucene的高亮显示输出，重置高亮
 */
public class ScanHightLighter {
	public ScanString[] ss;
	public String highlight;
	public String str;
	public boolean hit;
	public double score;
	
	public ScanHightLighter(SearchResult result) {
		//System.out.println(highter);
		String highter = result.Sent;
		String []hl = highter.split(" ");
		score = result.score;
		ss = new ScanString[hl.length];
		boolean br = false;
		int max = 0, min = 0;
		for(int i = 0; i < hl.length; i++) {
			if(hl[i].startsWith("<B>") && hl[i].endsWith("</B>")) {
				ss[i] = new ScanString(hl[i].substring(3, hl[i].length() - 4), true);
				max = i;
				if(!br) {
					br = true;
					min = i;
				}
			}
			else {
				ss[i] = new ScanString(hl[i], false);
			}
		}
		
		int count = 1;
		int tmpcount = 0;
		int l = min, r = max;

		hit = false;
		for(int i = min; i <= max; i++) {
			if(!ss[i].br) continue;
			for(int j = i; j <= max; j++) {
				if(!ss[j].br) continue;
				tmpcount = 0;
				for(int k = i; k <= j; k++) {
					if(ss[k].br) tmpcount++;
				}
				if(tmpcount >= count && (double)(j - i + 1)/tmpcount <= 2 ) {
					count = tmpcount;
					l = i;
					r = j;
					hit = true;
				}
			}
		}
		
		highlight = "";
		if(l > 0 && (ss[l-1].word.equalsIgnoreCase("the")||ss[l-1].word.equalsIgnoreCase("an")||ss[l-1].word.equalsIgnoreCase("a")))
			l--;
		if(r < ss.length - 1 && (ss[r+1].word.equalsIgnoreCase("to")||ss[r+1].word.equalsIgnoreCase("for")||ss[r+1].word.equalsIgnoreCase("of")||ss[r+1].word.equalsIgnoreCase("in")))
			r++;
		for(int i = l; i <= r; i++) {
			highlight = highlight + ss[i].word + " ";
		}
		str = "";
		for(int i = 0; i < ss.length; i++) {
			if(i >= l && i <= r) 
				str = str + "<B>" + ss[i].word + "</B> ";
			else
				str = str + ss[i].word + " ";
		}
	}
	
	public String getHightlight() {
		return highlight;
	}
	
	public String getTotal() {
		return str;
	}
	
	public boolean getHit() {
		return hit;
	}
	
}

class ScanString {
	public boolean br;
	public String word;
	ScanString(String word, boolean br) {
		this.word = word;
		this.br = br;
	}
}