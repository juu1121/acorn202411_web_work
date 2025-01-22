<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//url(원래요청url)에 값이 있으면 위로 없으면 인덱스페이지로 보낸다
	//Get방식 파라미터 url이라는 이름으로 전달되는 값이 있는지 읽어와본다.
	String url =request.getParameter("url");  
	//만일 넘어오는 값이 없다면
	if(url==null){
		//로그인 후에 인덱스 페이지로 갈 수 있도록 한다.
		String cPath=request.getContextPath();
		url=cPath+"/index.jsp";
		
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/login-form.jsp</title>
</head>
<body>
	<div class="container">
		<h1>로그인 폼</h1>
		<form action="${pageContext.request.contextPath}/user/login.jsp" method="post">
			<%--로그인후에 이동할 페이지 정보도 같이 전송되도록한다. --%>
			<input type="hidden" name="url" value="<%=url %>" />
			<div>
				<label for="userName">아이디</label>
				<input type="text" name="userName" id="userName"/>
			</div>
			<div>
				<label for="password">비밀번호</label>
				<input type="password" name="password" id="password"/>
			</div>
			<button type="submit">로그인</button>
		</form>	
	</div>
</body>
</html>