<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/fortune.jsp</title>
</head>
<body>
	<div class="container">
		<h1>오늘의 운세페이지</h1>
		<p>오늘의 운세 : <strong>${fortuneToday }</strong></p>
		<a href="${pageContext.request.contextPath}/">인덱스로 가기</a>
	</div>
</body>
</html>