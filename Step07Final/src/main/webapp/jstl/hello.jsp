<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	JSTL 사용 설정하기
	url="import할 경로"
	prefix ="접두어"
 --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/webapp/jstl/hello.jsp</title>
</head>
<body>
	<% for(int i=0; i<10; i++){ %>
		<p>Hello Java Code <strong><%=i %></strong></p>
	<%} %>
	
	<h1>Java Standard Tag Librrary (JSTL)</h1>
	<c:forEach var="i" begin="0" end="9">
		<p>
			Hello JSTL <strong>${i}</strong>
		</p>
	</c:forEach>
</body>
</html>