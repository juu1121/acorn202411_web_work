<%@page import="test.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
MemberDto dto = new MemberDto(1, "김구라", "노량진");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>webapp/member.jsp</title>
</head>
<body>
	<p>번호: <strong><%=dto.getNum()%></strong></p>
	<p>이름: <strong><%=dto.getName()%></strong></p>
	<p>주소: <strong><%out.print(dto.getAddr());%></strong></p>
</body>
</html>