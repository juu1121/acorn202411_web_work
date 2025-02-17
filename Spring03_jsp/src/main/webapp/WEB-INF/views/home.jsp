<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/home.jsp</title>
</head>
<body>
	<div class="container">
		<h1>인덱스 페이지 입니다.</h1>
		<p>context path : <strong>${pageContext.request.contextPath}</strong></p>
		
		<h2>공지사항</h2>
		<ul>
			<c:forEach var="tmp" items="${notice }">
				<li>${tmp }</li>
			</c:forEach>
		</ul>
		
		<ul>
			<li><a href="${pageContext.request.contextPath}/fortune">오늘의 운세</a></li>
			<li><a href="${pageContext.request.contextPath}/fortune2">오늘의 운세2</a></li>
			<li><a href="${pageContext.request.contextPath}/member">회원 한명의 정보</a></li>
		</ul>
	</div>
</body>
</html>