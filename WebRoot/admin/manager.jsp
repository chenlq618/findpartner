<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">

<title>管理页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="./css/bootstrap.min.css" />
<link rel="stylesheet" href="./css/bootstrap-theme.min.css" />
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
</head>
<body>
	<div class="container">
		<nav class="navbar navbar-default" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">首页</a>
					 <a class="navbar-brand" href="#">测试</a> 
					 <a class="navbar-brand" href="#">开发</a>
				</div>
			</div>
		</nav>

	</div>
</body>
</html>
