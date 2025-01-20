<%@page import="test.member.dao.MemberDao"%>
<%@page import="test.member.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. 폼 전송되는 이름과 주소를 추출한다.
	String name = request.getParameter("name");
	String addr = request.getParameter("addr");
	//2. MemberDto객체에 담는다.
	MemberDto dto = new MemberDto();
	dto.setName(name);
	dto.setAddr(addr);
	//3. DB에 저장한다.
	MemberDao dao = new MemberDao();
	boolean isSuccess=dao.insert(dto);
	//4. 응답하기 (form제출도 요청이니까, 응답해야징)
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/insert.jsp</title>
<jsp:include page="/include/resource.jsp"></jsp:include>
</head>
<body>
	<div class="container mt-5">
		<h3>알림</h3>
		<%if(isSuccess){ %>
			<p class="alert alert-success">
				<strong><%=name %></strong>님의 정보를 저장했습니다.
				<a class="alert-link" href="list.jsp">목록보기</a>
			</p>
		<%}else{ %>
			<p class="alert alert-danger">
				회원정보 저장실패
				<a class="alert-link" href="insertform.jsp">다시작성</a>
			</p>
		<% } %>
	</div>
</body>
</html>