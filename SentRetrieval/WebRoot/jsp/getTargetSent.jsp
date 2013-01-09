<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="bean.SearchResult" %>
<%@ page import="java.util.ArrayList"%>
<jsp:useBean id="bean" scope="application" class="bean.SearchBean"/>
<jsp:useBean id ="convertBean" scope="application" class="bean.ChiConvertToEngBean"/>
<jsp:useBean id ="wordSegment" scope="application" class="word.segment.PathLabel"/>
<%   
      //out.print("<center><h2>Target Sentences</h2><br></center><table width=100% border=\"1\" id=\"targetSentTable\">");
      session.removeAttribute("targetSents");
     // session.removeAttribute("targetSentsNumber");
      String querySent  = (String) request.getParameter("querySent");
      querySent = URLDecoder.decode(querySent,"utf-8");
     // System.out.println(querySent);
      wordSegment.setSentence(querySent);
	  String newsearchWord = wordSegment.tokenization();
	  String[] chiwords = newsearchWord.split(" ");
	  newsearchWord = "";
	  for(String chiword : chiwords){
		if(chiword.indexOf("/") >= 0){
			String s = chiword.substring(0,chiword.indexOf("/"));
			if(s!= null)
				newsearchWord += s+" ";
		}
	  }
	  
	  ArrayList<String> targetSents = new ArrayList<String>();
	  String engSearchWord = convertBean.convertToTargetSentUsePhrase(newsearchWord);
	  if(engSearchWord!= null){
	  	ArrayList<SearchResult> result = bean.getEntireResult(engSearchWord);
		if(result != null){
			for(int i = 0;i< result.size();i++){
				if(i > 49)
					break;
				
				targetSents.add(result.get(i).getSent());
				
				if(i > 20)
					continue;
				out.print("<tr   ><td onclick= \"tdonclick2(this,-1)\" >"+result.get(i).getSent()+"</td></tr>");
				
			}
		

			//String[] s  = new String[targetSents.size()];
			//targetSents.toArray(s);
			session.setAttribute("targetSents",targetSents);
			int temp = targetSents.size() / 20;
			int totalpage = ((double) targetSents.size()/(double) 20) - temp > 0 ? temp+1: temp;
			String number = ""+totalpage;
			out.print(number);
			System.out.println("retrieval total "+ targetSents.size() + " page "+ number );
			//session.setAttribute("targetSentsNumber",number);
		}else{
			out.print("<tr><td >No Result</td></tr>");
		}
	  }							
     // out.print("</table>");
%> 