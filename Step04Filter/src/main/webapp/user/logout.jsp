<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	session.removeAttribute("id");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/logout.jsp</title>
</head>
<body>
	<script>
		alert("로그아웃되었습니다.");
		location.href="${pageContext.request.contextPath}/"
	</script>
</body>
</html>