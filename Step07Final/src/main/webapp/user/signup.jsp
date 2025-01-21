<%@page import="test.user.dao.UserDao"%>
<%@page import="test.user.dto.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. 폼 전송되는 가입 회원 정보를 읽어와서
	String userName=request.getParameter("userName");
	String password=request.getParameter("password");
	String email=request.getParameter("email");
	//2. UserDto 객체에 담아서 
	UserDto dto=new UserDto();
	dto.setUserName(userName);
	dto.setPassword(password);
	dto.setEmail(email);
	//3. UserDao 객체를 이용해서 DB 에 저장한다.
	boolean isSuccess=UserDao.getInstance().insert(dto);
	//4. 응답하기 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/signup.jsp</title>
</head>
<body>
	<div class="container">
		<h1>알림</h1>
		<%if(isSuccess){ %>
			<p>
				<strong><%=userName %></strong>님 가입 되었습니다.
				<a href="login-form.jsp">로그인 하러 가기</a>
			</p>
		<%}else{ %>
			<p>
				가입이 실패 했습니다.
				<a href="signup-form.jsp">다시 가입하러 가기</a>
			</p>
		<%} %>
	</div>
</body>
</html>
