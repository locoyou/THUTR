<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%
	int per_Page_Show = 20;
	ArrayList<String> ss = (ArrayList<String>) session.getAttribute("targetSents");
	/*for(String s : ss){
		System.out.println(s);
	}*/
	System.out.println("targetSents "+ ss.size());
	String pageNum  = (String) request.getParameter("pageNum");
	//System.out.println("target sent page number "+ pageNum);
	int n = Integer.parseInt(pageNum);
	int start = (n-1)*per_Page_Show;
	//System.out.println("start "+ start);
	int temp = ss.size()/per_Page_Show;
	int totalpage = ((double)ss.size()/(double) per_Page_Show)-temp>0 ? temp+1:temp;
	//System.out.println("total page "+ totalpage);
	if( n <  totalpage){
		for(int i= start; i < start+per_Page_Show ; i++ ){
			out.print("<tr   ><td onclick= \"tdonclick2(this,-1)\" >"+ ss.get(i)+ "</td></tr>");
		}
	}else{
		for(int i= start; i < ss.size() ; i++ ){
			out.print("<tr   ><td onclick= \"tdonclick2(this,-1)\" >"+ ss.get(i)+ "</td></tr>");
		}
	}
	
%>