<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test/game.jsp</title>
</head>
<body>
	<div class="container">
		<h1>게임페이지</h1>
		<%--로그인을해야 아이템구매할수있게 --%>
		<a href="${pageContext.request.contextPath}/test/protected/buy.jsp">game아이템구매</a>
		<p>신나게 놀아보아요</p>
		<a href="${pageContext.request.contextPath}/">인덱스로</a>
	</div>
</body>
</html>