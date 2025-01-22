<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member-only/play.jsp</title>
</head>
<body>
	<div class="container">
		<h1>로그인된 회원전용공간입니다.</h1>
		<p>
			<strong>${sessionDto.userName }</strong>님 신나게 놀아보아요
			<a href="${pageContext.request.contextPath}/">인덱스로</a>
		</p>
	</div>
</body>
</html>