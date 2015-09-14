<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'mulImg.jsp' starting page</title>
    
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
     <form action="team/getJoinTeamsInfoL.json" method="post" enctype="multipart/form-data">  
    file:<input type="file" name="file"  >
     files:<input type="file" name="myfiles"  multiple="multiple">
	token<input type="text" name="token"> 
    phone<input type="text" name="phone">
     menber<input type="text" name="menber">  
    teamName<input type="text" name="teamName">
    teamId<input type="text" name="teamId">
       endTime<input type="text" name="endTime">
     teamCount<input type="text" name="teamCount">
      isCheck<input type="text" name="isCheck">
    <input type="submit" value="submit">   
</form>  
     
  </body>
</html>
