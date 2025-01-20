<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<h1>연습하는인덱스페이지</h1>
		<ul>
		<li><a href="test?abc=바봉">test?abc=1_서블릿</a></li>
		<li><a href="test?abc=잉.jsp">test?abc=1.jsp_서블릿</a></li>
		<li><a href="test.jsp?abc=봉봉">test.jsp?abc=1_jsp</a></li>
		</ul>
		
		<form action="test" method="get">
			<input type="text" name="abc" placeholder="할말입력..."/>
			<button type = "submit">g전송</button>
		</form>
		
		<form action="test.jsp" method="get">
			<input type="text" name="abc" placeholder="할말입력..."/>
			<button type = "submit">g전송</button>
		</form>
		
	</div>
</body>
</html>