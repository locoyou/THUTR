<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%   
     /* 前半部分是获得server上webcontent的真实路径，
      * 因为获得一个FileInputStream必须使用"E:/webcontent/filename.txt"这种类型的真实路径，
      * 而不是"http://www.xxx.com/xxx/filename.txt"这种URL的形式。
      * dataname 是具体的文件相对于webcontent的路径 */
     String fileDownload = (String) request.getParameter("fileDownload");
     String path = request.getContextPath();
	 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	 
     String fullname = request.getSession().getServletContext().getRealPath("/") + fileDownload;
 	 fileDownload =  java.net.URLEncoder.encode(fileDownload,"UTF-8");
     // filename是保存时的默认文件名，经实验，在Firefox2和IE7中，这个文件名是有用的，在IE6中似乎没什么用，有兴趣的人再去研究研究吧
     //String filename = fullname.substring(fullname.lastIndexOf('/'));   
     int i = 0;   
     response.setContentType("text/plain"); 
 
     // 关键是设置这个Header，否则还是会直接在浏览器中打开的
     response.setHeader("Content-Disposition","attachment;filename = " + fileDownload);   
     java.io.FileInputStream fileInputStream = null;
     java.io.OutputStream outp = null;
     try{
     	fileInputStream= new java.io.FileInputStream(fullname);  
     	outp = response.getOutputStream();
     	byte[] b = new byte[1024];
     
     	while((i= fileInputStream.read(b)) > 0){   
        	 outp.write(b,0,i);   
     	}  
     	outp.flush();
     }catch(java.io.IOException e){
     	System.out.println(e.getMessage());
     }finally{
     	if(fileInputStream != null){
     		fileInputStream.close();     		
     	}
     	if(outp != null){
     		outp.close();
     	}
     	response.flushBuffer();
     	out.clear();
     	out = pageContext.pushBody();
     }
    
%> 