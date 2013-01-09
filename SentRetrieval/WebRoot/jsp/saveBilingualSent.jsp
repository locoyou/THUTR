<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="org.thunlp.io.TextFileWriter" %>

<%
	String serverFilePath = request.getSession().getServletContext().getRealPath("/");
	
	System.out.println("file path "+ serverFilePath);
	String querySent  = (String) request.getParameter("querySent");
    querySent = URLDecoder.decode(querySent,"utf-8");
	
	String targetSent = (String) request.getParameter("targetSent");
	targetSent = URLDecoder.decode(targetSent,"utf-8");
	
	String fileDownload = (String) request.getParameter("fileName");
	fileDownload = URLDecoder.decode(fileDownload,"utf-8");
	if(querySent!= null && targetSent!= null){
		out.print("<p>"+querySent+"</p>");
		out.print("<p>"+targetSent+"</p>");
		out.print("<p>保存成功!</p>");
		targetSent = targetSent.replaceAll("<b>","");
		targetSent = targetSent.replaceAll("</b>","");
		TextFileWriter tw = new TextFileWriter(serverFilePath+fileDownload,true);
		tw.append(querySent+"\r\n"+targetSent+"\r\n");
		tw.flush();
		tw.close();
						
	}else{
		out.print("<p>源语言句子或目标语言句子为空，保存失败</p>");
	}
%> 