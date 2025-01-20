<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg = request.getParameter("msg");
	System.out.println("msg:"+msg);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/jquery/send.jsp</title>
</head>
<body>
	<p>클라이언트에 메세지 잘 받았어</p>
</body>
</html>