<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.lang.*"%>
<%@ page import="bean.SearchResult" %>
<%@ page import="bean.SortByCount" %>
<%@ page import="bean.ScanHightLighter" %>
<%@ page import="bean.MosesDecoder" %>
<%@ page import="bean.ConfiFile" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="bean" scope="application" class="bean.SearchBean"/>
<jsp:useBean id ="convertBean" scope="application" class="bean.ChiConvertToEngBean"/>
<jsp:useBean id ="wordSegment" scope="application" class="word.segment.PathLabel"/>
<%request.setCharacterEncoding("UTF-8"); %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
convertBean.init(new File("."+ File.separator + "webapps" + File.separator + "TRdemo" + File.separator + "configFile.conf").getAbsolutePath());
//convertBean.init("/var/lib/tomcat6/webapps/configFile.conf");
%>
<%
	String searchWord = request.getParameter("searchWord");
	String more = request.getParameter("more");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel=stylesheet type="text/css" href="css/style.css" />
    <title> Search page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
   	
  <body>
     <br>
     <br>
     <div class="wrapper">
     	
         <%
		 	if(more == null && (searchWord == null || searchWord.equals(""))){
		 %>				
			 <h1 id="title" align="center"> Translation Retrieval<br> </h1>
		 <%					
		 }else{
		 %>
			 <h1 id="title" align="left"> Translation Retrieval<br> </h1>
		 <% 
		 }
 		 %>					
     	
    	 <br>
			<form method=post action ="search.jsp">
				<table>
						<tr>
							<td>
							<%
							if(searchWord == null){
							%>
							<input name=searchWord id=searchWord type=text  
								maxlength="1024" value="" style="width:730px;"/>
							<%
							}else{
							%>
							<input name=searchWord id=searchWord type=text  
								maxlength="1024" value="<%=searchWord%>" style="width:730px;"/>
							<% 
							}
							%>
							</td>
							<td><input id=doSearch type=submit value="Search"/>
							</td>
						</tr>
					
				</table>
			</form>
     		<form >
     			<table class="result" >
     				<tbody>
     				<%
     				if(more != null) {

     					ArrayList<ScanHightLighter> list = (ArrayList<ScanHightLighter>)session.getAttribute(more);
     					
     					for(int i = 0;i< list.size()&&i<50;i++){
							int k = i + 1;
						%>									
							<tr><td class="number" valign=top><%=k+":"%>&nbsp;</td><td class="title" ><p><%=list.get(i).str%></p></td></tr>
							<tr><td></td><td></td></tr>
						<%		
			  		 	}
     				}
					else if(searchWord != null && searchWord.length() > 8){
						wordSegment.setSentence(searchWord);
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
						//System.out.println(newsearchWord);
						//String engSearchWord = convertBean.convertToTargetSent(newsearchWord);
						String engSearchWord = convertBean.convertToTargetSentUsePhrase(newsearchWord);
						//System.out.println(engSearchWord);
						if(engSearchWord!= null){
							ArrayList<SearchResult> result = bean.getEntireResult(engSearchWord);
							if(result != null){
								for(int i = 0; i < result.size() && i < 50;i++){
									int k = i + 1;
									%>									
									<tr><td class="number" valign=top><%=k+":"%>&nbsp;</td><td class="title" ><p><%=result.get(i).getSent()%></p></td></tr>
									<tr><td></td><td></td></tr>
									<%		
			  		 			}
		    				}else{
								%>
		    					<tr><td class="title"> <p>No Result</p></td></tr>
								<%
		   					}
		  				}
					}
					else if(searchWord != null && searchWord.length() > 0) {
						wordSegment.setSentence(searchWord);
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
						String engSearchWord = convertBean.convertToTargetSentUsePhrase(newsearchWord);
						engSearchWord = "("+engSearchWord+") OR ("+ convertBean.convertToTargetUseDict(newsearchWord)+")";
						if(engSearchWord!= null){
							ArrayList<SearchResult> result = bean.getEntireResult(engSearchWord);
							if(result != null){
								SortByCount sbc = new SortByCount(result, newsearchWord);
								ArrayList<Map.Entry<String, ArrayList<ScanHightLighter>>> sorted = sbc.getSorted();
								for(int i = 0; i < sorted.size() && i < 50;i++){
									int k = i + 1;
									%>									
									<tr><td class="number" valign=top><%=k+":"%>&nbsp;</td><td class="title" ><p><b><%=sorted.get(i).getValue().get(0).highlight+" "%></b></p>
									<p><font style="font-size:11px;">About&nbsp;<%=sorted.get(i).getValue().size()%>&nbsp;results</font></p>
									<% 
									for(int j = 0; j < sorted.get(i).getValue().size() && j < 3; j++) {
										%>
										<p><b>*&nbsp;</b><%=sorted.get(i).getValue().get(j).str%></p>
									<% 
									}
									session.setAttribute("more"+k, sorted.get(i).getValue());
									%>
									<p><a href=search.jsp?more=more<%=k%>>more</a></p>
									</td></tr>
									<tr><td></td><td></td></tr>
									<%		
			  		 			}
		    				}else{
								%>
		    					<tr><td class="title"> <p>No Result</p></td></tr>
								<%
		   					}
		  				}
					}
					%>
					</tbody>
				</table>		
 			</form>
 	  	<div style="clear:both"></div>
    </div>
 	<div class="footer">
 		<br>
		<br>
		<br>
		<center>
			<span> © 2011－2012 thunlp, all rights reserved. <a
				href="http://nlp.csai.tsinghua.edu.cn/" target="_blank">清华大学自然语言处理组</a>
			</span>
		</center>
	</div>
  </body>
</html>
