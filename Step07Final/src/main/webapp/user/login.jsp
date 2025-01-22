<%@page import="java.net.URLEncoder"%>
<%@page import="test.user.dto.SessionDto"%>
<%@page import="test.user.dao.UserDao"%>
<%@page import="test.user.dto.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. 폼전송되는 userName, password를 읽어와서
	String userName=request.getParameter("userName");
	String password=request.getParameter("password");
	//2. id에 해당하는 회원정보를 UserDao객체를 이용해서 얻어와서
	UserDto dto=UserDao.getInstance().getData(userName);
	//3. 실제로 존재하는 아이디이고 존재한다면 비밀번호도 일치하는지 비교해서
	boolean isLoginSuccess=false;
	if(dto != null){
		//비밀번호도 일치하면
		if(dto.getPassword().equals(password)){
			//로그인프라이머리키, userName, role(3가지를 session영역에 담음) => session영역에 담을 정보를 담는 객체를 생성!
			//로그인 처리를 한다(로그인 된 user정보를 session영역에 담는다.)
			SessionDto sessionDto=new SessionDto();
			sessionDto.setNum(dto.getNum());
			sessionDto.setUserName(dto.getUserName());
			sessionDto.setRole(dto.getRole());
			//로그인처리해주기
			session.setAttribute("sessionDto", sessionDto);
			isLoginSuccess=true;
		}
	
	}
	
	//로그인후 가야할 목적지정보 (원래가려던목적지일수도있고, 인덱스일수도있다.)
	String url=request.getParameter("url");
	//로그인 실패를 대비해서 목적지 정보를 인코딩한 결과도 준비한다.
	String encodedUrl=URLEncoder.encode(url, "UTF-8");
	//일치하면 로그인 처리후 응답, 일치하지 않으면 일치하지 않는다고 응답
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/login.jsp</title>
</head>
<body>
	<div class="container">
		<%if(isLoginSuccess){ %>
			<p>
				<strong><%=dto.getUserName() %></strong>님 로그인 되었습니다.
				<a href="<%=url%>">확인</a>
			</p>
		<%}else{ %>
			<p>
				아이디 혹은 비밀번호가 틀려요
				<a href="${pageContext.request.contextPath}/user/login-form.jsp?url=<%=encodedUrl %>">다시 입력</a>
			</p>
		<%} %>
	</div>
</body>
</html>