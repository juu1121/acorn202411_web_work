<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	//request scope에 "fortuneToday"라는 키값으로 담겨있는 String type 데이터 얻어오기
	//오브젝트로 리턴되기때문에 타입캐스팅필요
	String fortuneToday=(String)request.getAttribute("fortuneToday");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/test/fortune.jsp</title>
</head>
<body>
	<div class="container">
		<h1>운세페이지</h1>
		<p>오늘의 운세 : <strong><%=fortuneToday %></strong></p>
		<%--Expression Language를 이용하면 request영역에 담긴 데이터를 편하게 추출할수있다. --%>
		<%--EL안에다가 'requestScope.키값'넣으면, 위에 String fortuneToday담는 코드 필요없음 --%>
		<p>오늘의 운세 : <string> ${requestScope.fortuneToday} </string></p>
		<%-- requestScope는 생략가능하다--%>
		<p>오늘의 운세 : <string> ${fortuneToday} </string></p>
	</div>
</body>
</html>