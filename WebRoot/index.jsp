<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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
    <form action="user/regist.json" method="post" enctype="multipart/form-data">  
    file:<input type="file" name="file"  >
    file2:<input type="file" name="myfiles"  multiple="multiple" >  
    
    phone<input type="text" name="phone">  
    deviceId<input type="text" name="deviceId">
    token<input type="text" name="token">
    password<input type="text" name="password">  
    universityId<input type="text" name="universityId">  
    <input type="submit" value="submit">   
</form>  
  </body>
</html>
