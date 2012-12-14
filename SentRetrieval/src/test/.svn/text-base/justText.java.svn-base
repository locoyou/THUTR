package test;

import java.util.ArrayList;

public class justText {
	public void test(ArrayList<String> list){
		ArrayList<String> templist = new ArrayList<String>();
		templist.add("d");
		templist.add("e");
		list.clear();
		list.add("x");
		list.add("y");
		list = templist;
		return;
		//return templist;
	}
	public void pro(){
		ArrayList<String> l = new ArrayList<String>();
		l.add("a");
		l.add("b");
		l.add("c");
		test(l);
		System.out.println(l.size());
		System.out.println(l.toString());
	}
	public static void main(String[] args){
		justText t = new justText();
		t.pro();
	}
}
