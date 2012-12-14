package tools;

import java.util.Comparator;

public class WordProComparator implements Comparator<WordProPair> {

	public int compare(WordProPair o1, WordProPair o2) {
		// TODO Auto-generated method stub
		if(o1.Prob > o2.Prob)
			return -1;
		else if(o1.Prob == o2.Prob)
			return 0;
		else
			return 1;
	}

}
