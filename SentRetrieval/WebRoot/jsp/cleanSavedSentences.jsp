<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.io.File"%>
<%
   String fullname = request.getSession().getServletContext().getRealPath("/") +"BilingualSentences.txt";
   File f = new File(fullname);
   if(f.exists()){
   		if(f.delete()){
   			out.print("true");
   		}else{
   			out.print("false");
   		}
   }else{
   		out.print("noexist");
   }
   	 
%> 