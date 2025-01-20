<%@page import="test.member.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. GET방식 파라미터로 전달되는 삭제할 맛집의 번호를 얻어온다.
	int num = Integer.parseInt(request.getParameter("num"));
	//2. MemberDao객체를 이용해서 실제 DB에서 삭제
	MemberDao dao = new MemberDao();
	boolean isSuccess= dao.delete(num);
	//3. 응답하기
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>food/delete.jsp</title>
<jsp:include page="/include/resource.jsp"></jsp:include>
</head>
<body>
	<div class="container mt-5">
		<h3>알림</h3>
		<%if(isSuccess){ %>
			<p class="alert alert-success">
				<strong><%=num %></strong>번 회원의 정보를 삭제했습니다.
				<a class="alert-link" href="list.jsp">확인</a>
			</p>
		<%}else{ %>
			<p class="alert alert-danger">
				삭제 실패!
				<a class="alert-link" href="list.jsp">확인</a>
			</p>
		<%} %>
	</div>
</body>
</html>