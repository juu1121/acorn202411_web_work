<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String msg = request.getParameter("abc");
PrintWriter pw = response.getWriter();
pw.println("문자열: "+msg);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test.jsp</title>
</head>
<body>
	<h1>test.jsp페이지</h1>
	<h1>pass 푸쉬</h1>
</body>
</html>