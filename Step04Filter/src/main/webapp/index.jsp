<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//세션에 저장된 아이디가 있는지 읽어와본다.
	//jsp에서session은 기본객체니까 바로 쓸 수있음 -> 만약 session이없다면=request.getsession으로 가져오기!
	String id=(String)session.getAttribute("id");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index</title>
</head>
<body>
	<div class="container">
		<%if(id==null){ %>
			<a href="user/loginform.jsp">로그인하러가기</a>
		<%}else{ %>
			<strong><%=id %></strong>님 로그인중...
			<a href="user/logout.jsp">로그아웃</a>
		<%} %>
		<h1>인덱스 페이지 입니다.</h1>
		<ul>
			<li><a href="test/study.jsp">공부하러가자</a></li>
			<li><a href="test/game.jsp">게임하러가기</a></li>
			<li><a href="admin/manage.jsp">관리자페이지</a></li>
		</ul>
	</div>
</body>
</html>