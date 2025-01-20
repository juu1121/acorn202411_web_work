<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//session scope에 "nick"이라는 키값으로 저장된 값 삭제하기
	session.removeAttribute("nick");
	//session scope에 저장된 모든 내용 초기화
	session.invalidate();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/test/logout.jsp</title>
</head>
<body>
	<p>
		로그아웃되었습니다.
		<a href="${pageContext.request.contextPath}/index.jsp">인덱스로</a>
	</p>
</body>
</html>