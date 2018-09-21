<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
 <script type="text/javascript"> var webContext="${pageContext.request.contextPath}"</script>
  <script src="${pageContext.request.contextPath}/static/js/jquery-2.1.1.min.js" type="text/javascript"></script>

</head>
<body>
<h1>myHome 应用</h1>
<br/>
${sessionScope.username}登录成功&nbsp;&nbsp;<a  href="http://10.7.10.250:8089/cas/logout" >登出</a>
<br/>

<a  href="http://10.7.10.250:7788/myTest/index.jsp">进去myTest应用系统</a>
</body>
</html>
