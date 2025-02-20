<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/WEB-INF/views/home.jsp</title>
</head>
<body>
	<div class="container">
		<h1>인덱스 페이지입니다(jsp)</h1>
		<ul>
			<li><a href="${pageContext.request.contextPath}/member/list">회원 목록보기</a></li>

		</ul>
	</div>
</body>
</html>