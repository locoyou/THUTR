<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://nlp.csai.tsinghua.edu.cn/">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.lang.*"%>
<%@ page import="bean.SearchResult" %>
<%@ page import="org.thunlp.io.TextFileReader" %>
<%@ page import="org.thunlp.io.TextFileWriter" %>
<%@ page import="java.io.File" %>
<%request.setCharacterEncoding("utf-8");%>
<jsp:useBean id="bean" scope="application" class="bean.SearchBean"/>
<jsp:useBean id ="convertBean" scope="application" class="bean.ChiConvertToEngBean"/>
<jsp:useBean id ="wordSegment" scope="application" class="word.segment.PathLabel"/>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println(basePath);
//convertBean.init(new File(".."+ File.separator + "webapps" + File.separator + "SentRetrieval" + File.separator + "configFile.conf").getAbsolutePath());
//convertBean.init("D://Workspaces/SentRetrieval/configFile.conf");
int totalPage = 0;
%>

<title>Building Parallel Corpus</title>

<style type="text/css">
div#container{width:100%}
div#header {background-color:#99bbbb;text-align:center;}
div#sourcesent {background-color:#ffff99;width:40%;float:left;}
div#controlpanel {background-color:#EEEEEE;width:20%;float:left;}
div#targetsent {background-color:#FFF;heigh:300px;width:40%;float:right;}

div#sourcesenttail{background-color:#ffff99;;width:40%;float:left;}
div#controlpaneltail{background-color:#EEEEEE;width:20%;float:left;}
div#targetsenttail {background-color:#FFF;width:40%;float:right;}

div#sourcesenthead {background-color:#ffff99;heigh:10%;width:40%;float:left;}
div#controlpanelhead {background-color:#EEEEEE;heigh:10%;width:20%;float:left;}
div#targetsenthead {background-color:#FFF;heigh:10%;width:40%;float:left;}

div#footer {background-color:#99bbbb;clear:both;text-align:center;}
h1 {margin-bottom:0;}
h2 {margin-bottom:0;font-size:18px;}
ul {margin:0;}
li {list-style:none;}
</style>

<!--mce:script src="lib/jquery-1.8.2.js" mce_src="jquery-1.8.2.js" type="text/javascript" ></mce:script-->
<!--mce:script src="my.js" mce_src="my.js" type="text/javascript"></mce:script-->
<style type="text/css">
		#divSCA
        {
            position: absolute;
            width: 400px;
            height: 230px;
            font-size: 12px;
            background: #fff;
            border: 0px solid #000;
            z-index: 10001;
            display: none;
        }
        .showpage:hover{
            cursor:pointer;
        }
</style>

<script type="text/javascript" src="js/jquery_last.js"></script>
<script type="text/javascript" src="js/jquery.divbox.js"></script>
<script type="text/javascript" language="javascript">
var pagenumber = 1;
var targetpagenumber = 1;
function showTargetNextPage(signal){
	var xmlhttp;
	if (window.XMLHttpRequest)
  	{// code for IE7+, Firefox, Chrome, Opera, Safari
  		xmlhttp=new XMLHttpRequest();
  	}
	else
  	{// code for IE6, IE5
  		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  	}
  	xmlhttp.onreadystatechange=function()
  	{
  		if (xmlhttp.readyState==4 && xmlhttp.status==200)
    	{
    		if(xmlhttp.responseText == null)
    		{
    			return;
    		}
    		document.getElementById("targetSentTable").innerHTML=xmlhttp.responseText;
    		document.getElementById("targetsentence").value = "";
    		document.getElementById("targetpageNumber").innerHTML = "第"+targetpagenumber+"页";
    		onresize(null);
    	}
    }
    var totalPageNumber = parseInt(document.getElementById("totalTargetPageNumber").innerHTML,10);
    if(signal == true && targetpagenumber == totalPageNumber){
    	alert("已经是最后一页了");
    	return;
    }
    if(signal)
    	targetpagenumber = targetpagenumber+1;
    else{
    	if(targetpagenumber-1 == 0){
    		alert("已经是第一页了");
    		return;
    	}
    	targetpagenumber = targetpagenumber-1;
    		
    }
    
    xmlhttp.open("GET","jsp/showTargetPage.jsp?pageNum="+targetpagenumber,true);
	xmlhttp.send();
}
function showNextPage(signal){
	if(!document.getElementById("sourceSentTable").innerHTML.match("<td")){
		alert("请先选择上传文件");
		return;
	}
	var xmlhttp;
	if (window.XMLHttpRequest)
  	{// code for IE7+, Firefox, Chrome, Opera, Safari
  		xmlhttp=new XMLHttpRequest();
  	}
	else
  	{// code for IE6, IE5
  		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  	}
  	xmlhttp.onreadystatechange=function()
  	{
  		if (xmlhttp.readyState==4 && xmlhttp.status==200)
    	{
    		if(xmlhttp.responseText == null)
    		{
    			return;
    		}
    		document.getElementById("sourceSentTable").innerHTML=xmlhttp.responseText;
    		document.getElementById("pageNumber").innerHTML = "第"+pagenumber+"页";
    		document.getElementById("targetSentTable").innerHTML="";
    		document.getElementById("querysentence").value = "";
    		document.getElementById("targetsentence").value = "";
    		onresize(null);
    		
    	}
    }
    var totalPage = parseInt(document.getElementById("totalPageNumber").innerHTML,10);
    if(signal == true && pagenumber == totalPage){
    	alert("已经是最后一页了");
    	return;
    }
    if(signal)
    	pagenumber = pagenumber+1;
    else{
    	if(pagenumber-1 == 0){
    		alert("已经是第一页了");
    		return;
    	}
    	pagenumber = pagenumber-1;
    		
    }
    	
    xmlhttp.open("GET","jsp/showPage.jsp?pageNum="+pagenumber,true);
	xmlhttp.send();
}
function openDiv() {
	$("#divSCA").OpenDiv();
}
function closeDiv() {
	$("#divSCA").CloseDiv();	
}

function saveSubmitType( type){
	if(type == 3){
		var queryindex  = parseInt(document.getElementById("selectQueryIndex").value,10);
		var targetindex = parseInt(document.getElementById("selectTargetIndex").value,10);
		if(isNaN(queryindex) || isNaN(targetindex)){
			alert("请同时选择要保存的源语言和目标语言句子");
			return ;
		}
		if(queryindex != parseInt(document.getElementById("selectQueryIndex2").value,10)){
			alert("您当前选择的源语言句子并不是目标语言译文所对应的句子，请重新选择");
			return ;
		}
		var xmlhttp;
			if (window.XMLHttpRequest)
  			{// code for IE7+, Firefox, Chrome, Opera, Safari
  				xmlhttp=new XMLHttpRequest();
  			}
			else
  			{// code for IE6, IE5
  				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  			}
			xmlhttp.onreadystatechange=function()
  			{
  				if (xmlhttp.readyState==4 && xmlhttp.status==200)
    			{
    				document.getElementById("targetSentTable").innerHTML=xmlhttp.responseText;
    				document.getElementById("selectTargetIndex").value = "";
    				document.getElementById("targetsentence").value ="";
    				onresize(null);
    				var selectQueryIndex = document.getElementById("selectQueryIndex");
					var index1 =  parseInt(selectQueryIndex.value ,10);
					if(!isNaN(index1)){
						tdonclick(null,index1);
					}
    				
    			}
  			}
  			var querySent = document.getElementById("querysentence").value;
  			querySent = encodeURI(encodeURI(querySent));
  			var targetSent = document.getElementById("targetsentence").value;
  			targetSent = encodeURI(encodeURI(targetSent));
  			var fileName =  encodeURI(encodeURI("BilingualSentences.txt"));
  			xmlhttp.open("GET","jsp/saveBilingualSent.jsp?querySent="+querySent+"&targetSent="+targetSent+"&fileName="+fileName,true);
			xmlhttp.send();
			
		
	}
	if(type == 2){
		targetpagenumber = 1;
		var queryindex  = parseInt(document.getElementById("selectQueryIndex").value,10);
		if(isNaN(queryindex)){
			alert("请选择要检索的源语言句子");
			return ;
		}else{
			document.getElementById("selectQueryIndex2").value = queryindex;
			var querySent = document.getElementById("querysentence").value;
			
			var xmlhttp;
			if (window.XMLHttpRequest)
  			{// code for IE7+, Firefox, Chrome, Opera, Safari
  				xmlhttp=new XMLHttpRequest();
  			}
			else
  			{// code for IE6, IE5
  				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  			}
			xmlhttp.onreadystatechange=function()
  			{
  				if (xmlhttp.readyState==4 && xmlhttp.status==200)
    			{
    				document.getElementById("targetSentTable").innerHTML=xmlhttp.responseText;
    				onresize(null);
    				tdonclick2(null,0);
    				document.getElementById("selectQueryIndex2").vaule =  queryindex;
    				document.getElementById("targetpageNumber").innerHTML = "第"+targetpagenumber+"页";
    				var totalPageNumber = "<%=session.getAttribute("targetSentsNumber")%>";
   					//var totalNumber = parseInt(totalPageNumber,10);
   					document.getElementById("totalTargetPageNumber").innerHTML = 2;
    			}
  			}
  			//alert("querySent "+ querySent);
  			querySent = encodeURI(encodeURI(querySent));
  			xmlhttp.open("GET","jsp/getTargetSent.jsp?querySent="+querySent+"&timestamp="+new Date().getTime(),true);
			xmlhttp.send();
		}
	}
	document.getElementById("submittype").value = type;
	
	return true;
}

function validateUpload(){
	var fileUpload = document.getElementById("fileUpload").value;
	if(fileUpload != ""){
		return true;
	}
	else{
		alert("请选择文件路径");
		return false;
	}
	
}

function onresize(obj){
	var left = document.getElementById("sourcesent");
	var center = document.getElementById("controlpanel");
	var right = document.getElementById("targetsent");
	
	var SourceHeight =  parseInt(left.clientHeight, 10);
	var CenterHeight =  parseInt(center.clientHeight,10);
	var TargetHeight =  parseInt(right.clientHeight,10);
	
	var RightTableHeight = parseInt(document.getElementById("targetSentTable").clientHeight,10);
	var LeftTableHeight =  parseInt(document.getElementById("sourceSentTable").clientHeight,10);
	
	//alert("left "+SourceHeight+" center "+CenterHeight+" right "+TargetHeight +" rightTable "+ RightTableHeight);
	TargetHeight = RightTableHeight;
	SourceHeight = LeftTableHeight;
	CenterHeight = 264;
	
	var maxHeight = (SourceHeight > CenterHeight)? SourceHeight:CenterHeight;
	maxHeight = (maxHeight > TargetHeight)? maxHeight:TargetHeight;
	if(maxHeight < 600)
		maxHeight = 600;
		
	left.style.height = maxHeight+"px";
	right.style.height = maxHeight+"px";
	center.style.height = maxHeight+"px";
	
}
	
function tdonclick(obj ,index){
	var table = document.getElementById("sourceSentTable");
	var tdd = table.getElementsByTagName("td");
	var tds = new Array();
	//以下对应于点击保存之后调用的情形
	if(index != -1){
		var count = 0;
		var content = "<table width=100%  border=\"1\" id=\"sourceSentTable\">";
		for(var i = 0; i < tdd.length; i ++){
			if(i != index){
				tds[count] = tdd[i];
				content = content+"<tr   ><td onclick= \"tdonclick(this,-1)\" >"+tdd[i].innerHTML +"</td></tr>";
				count++;
			}		
		}
		content = content +"</table>"
  	  	table.innerHTML = content;
  	  	
  	  	tds = table.getElementsByTagName("td");				
		if(tds.length == 0){
			document.getElementById("querysentence").value = "";
			document.getElementById("selectQueryIndex").value ="";
		}
		if(index > tds.length-1){ //对应最后一个，index-1同时改变文本框内容
			index = index -1;
			document.getElementById("selectQueryIndex").value = index;
			tds[index].style.backgroundColor="#99bbbb";
			var input = document.getElementById("querysentence");
			input.value = tds[index].innerHTML;
		}
		else {	//index 不需要改变，仅改变文本框中的内容
			tds[index].style.backgroundColor="#99bbbb";
			var input = document.getElementById("querysentence");
			input.value = tds[index].innerHTML;
		}
		
		return;
	}
	//以下对应于仅仅点击表格使之变色的情形
	var index = document.getElementById("selectQueryIndex");
	for(var i=0; i <tdd.length; i++){
		var td = tdd[i];
		td.parentNode.style.backgroundColor="#99bbbb";
		if(td == obj){
			td.style.backgroundColor="#99bbbb";
			index.value = i;
		}else{
			td.style.backgroundColor="#ffff99";
		}
	}
	var input = document.getElementById("querysentence");
	input.value = obj.innerHTML;

}
function tdonclick2(obj,index){
	var table = document.getElementById("targetSentTable");
	if(table == null)
		return;
	var tds = table.getElementsByTagName("td");
	if(index != -1){
		if(tds.length != 0){
			tds[0].style.backgroundColor="#99bbbb";
			var input = document.getElementById("targetsentence");
			document.getElementById("selectTargetIndex").value = index;
			input.value = tds[0].innerHTML;
		}
		return;
	}
	
	
	index = document.getElementById("selectTargetIndex");
	for(var i=0; i <tds.length; i++){
		var td = tds[i];
		td.parentNode.style.backgroundColor="#FFF";
		if(td == obj){
			td.style.backgroundColor="#99bbbb";
			index.value = i;
		}else{
			td.style.backgroundColor="#fff";
		}
	}
	var input = document.getElementById("targetsentence");
	if(obj != null)
		input.value = obj.innerHTML;
}


</script>

</head>

<body onload="onresize(this)" onresize="onresize(this)" >
<div id="container">

<div id="header">
	<h1>Building Parallel Corpus Page</h1>
</div>
<div id="sourcesenthead">
<center>
<h2>Source Sentences</h2>
<br>
</center>
</div>

<div  id="controlpanelhead">
	<center>
  	<h2>Control Panel</h2>
  	</center>
  	<br>
</div>

<div id="targetsenthead">
<center>
<h2>Target Sentences</h2>
</center>
<br>
</div>
<%
  int itotalRead= 0,ireadByte= 0;
  String paraname="";
  String fileupload = (String) request.getParameter("fileupload");
  String fileDownload = "BilingualSentences.txt";
  ArrayList<String> displaySent = new ArrayList<String>();
  if(request != null){
  	
 	paraname = (String)request.getParameter("sentence");
 	System.out.println("parameter "+ paraname);
 	
 	//if(submittype == null){	 // 对应提交文件
  		int itotalByte = request.getContentLength();
  		byte[] Buffer;
  		if(itotalByte >0){
     		Buffer = new byte[itotalByte];
     		for(; itotalRead < itotalByte;itotalRead+=ireadByte){
	 			try{
					ireadByte = request.getInputStream().read(Buffer,itotalRead,itotalByte-itotalRead);
				}catch(Exception e){
					e.printStackTrace();
				}
	 		}
	 		String strBuffer = new String(Buffer);
  	 		String[] lines = strBuffer.split("\r\n");
  	 		
  	 		session.removeAttribute("displaySents");
  	 		if(lines.length > 4){
  	 			
  	 			for(int i = 4; i < lines.length-1;i++){
  	 				if(!lines[i].equals("")){
  	 					displaySent.add(lines[i]);
  	 				}
  	 			}
  	 			if(displaySent.size()>0){
  	 				int temp = displaySent.size()/20;
  	 				totalPage = ((double) displaySent.size()/(double) 20) - temp > 0 ? temp+1:temp;
  	 				session.setAttribute("displaySents",displaySent);
  	 			}
  	 		}
  		}
 	//}
 	
 	
  }else
  	System.out.println("requeset is null");
%>

<div id="sourcesenttail">
<center>
<a id="pageNumber"> 第1页</a>
<a  class = "showpage" onclick="showNextPage(false)" ><font color=blue>上一页</font></a>
<a  class = "showpage" onclick="showNextPage(true)"  ><font color=blue>下一页</font></a>
<a>共</a><a id="totalPageNumber"><%=totalPage%></a><a>页</a>
</center>
</div>

<div  id="controlpaneltail">
	<center>
	<a>&nbsp;</a>
	<br>
  	</center>
</div>

<div id="targetsenttail">
<center>
	<a id="targetpageNumber"> 第1页</a>
	<a  class = "showpage" onclick="showTargetNextPage(false)" ><font color=blue>上一页</font></a>
	<a  class = "showpage" onclick="showTargetNextPage(true)"  ><font color=blue>下一页</font></a>
	<a>共</a><a id="totalTargetPageNumber">0</a><a>页</a>
</center>
</div>



<div id="sourcesent">
<table width=100%  border="1" id="sourceSentTable">
 
<%
	if(displaySent.size() > 0){
  		int count = 0;
  		for(String line : displaySent){
  			count++;
  			if(count < 21){
  	 		%>
  	  	    	<tr   >
    			<td onclick= "tdonclick(this,-1)" ><%=line%></td>
  	  			</tr>
  	 		<%
  			}else
  				break;
  		}
  	}
	

%>

</table>
</ul>
</div>


 <div id="controlpanel">
  <FORM NAME="oForm"
   	ACTION="newdemo.jsp"
   	ENCTYPE="multipart/form-data"
   	METHOD="post">
   	<center>
   	<%
   	if(fileupload != null){
   	%>
   	<INPUT TYPE="file" ID="fileUpload" NAME="oFile1"/ style="display;width:100%" value="<%=fileupload%>">
   	<%
   	}else{
   	%>
   	<INPUT TYPE="file" ID="fileUpload" NAME="oFile1" style="display;width:100%" />
   	<%
   	}
   	%>
   	<br>
   	<br>
	<INPUT TYPE="submit" VALUE="上传文件" style="width: 80px" onclick="return validateUpload()" >
  	</center>
  </FORM>
  <FORM ACTION="newdemo.jsp" 	METHOD="post">
  	<br>
  	<center>
  	
  
  	<input id="querysentence" name="sentence" style="display;width:100%" />
  	
  	
  	<input id="targetsentence" name="targetsentence" style="display;width:100%"/>
  	
  	
  	
  		<input id="selectQueryIndex" name="selectedQueryIndex" style="display:none;width:100%"> 
  	
  	
  		<input id="selectTargetIndex" name="selectedTargetIndex" style="display:none;width:100%">
  	
  		<input id="selectQueryIndex2" name="selectedQueryIndex2" style="display:none;width:100%"> 
   		<br>
  		<INPUT TYPE="button" VALUE="检索"  style="width: 80px" onclick="saveSubmitType(2)">
  		<INPUT id="submittype" name="submittype" style="display:none">
  		<br>
  		<br>
  		<INPUT TYPE="button" VALUE="保存" style="width: 80px" onclick="saveSubmitType(3)">
  	</center>
  </FORM>
 <!-- <form ACTION="download.jsp" 	METHOD="post"> --> 
  <center>
  	<br>
  	<INPUT TYPE="submit" VALUE="下载" style="width: 80px" onclick="openDiv() ">
  </center>
 
</div>


<div id="targetsent" >
<table width=100% border="1" id="targetSentTable">
</table>
</div>



<div id="divSCA" >
		<div style="padding: 10px;background-color: #0296c4 ;height:40px;color: #FFFFFF;font-weight: bold;font-size: 15px;">信息提示</div> 
		<div style="padding: 10px;font-size: 13px;">
		    &nbsp;&nbsp;请点击以下链接下载
		    <center>
		   <a href="jsp/download.jsp?fileDownload=<%=fileDownload%>">下载</a> 
			</center>
		</div>
		<div align="center" style="padding-top: 10px;">
		   <input type="button" style="width: 50;height: 25px;" value="关 闭" onclick="closeDiv()">
		</div>
</div>

</div>



<div id="footer">
		<br>
		<center>
			<span> &copy; 2011－2012 thunlp, all rights reserved. <a
				href="http://nlp.csai.tsinghua.edu.cn/" target="_blank">清华大学自然语言处理组</a>
			</span>
		</center>
		<br>
</div>


</body>
</html>
