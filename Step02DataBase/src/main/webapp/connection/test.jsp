<%@page import="java.sql.Connection"%>
<%@page import="test.util.DbcpBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	//DbcpBean객체를 이용해서 Connection 객체 얻어오기
	Connection conn = new DbcpBean().getConn();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Webapp/connection/test.jsp</title>
</head>
<body>
	<div class="container">
		<h1>Connection 테스트페이지</h1>
		<%if(conn != null){ %>
			<!-- conn이 null이 아니면 여기에 있는 문자열이 클라이언트에게 출력 -->
			<p>Connection 객체를 성공적으로 얻어왔습니다.</p>
		<%} else{ %>
			<p>Connection 객체 얻어오기 실패!</p>
		<%} %>
	</div>
</body>
</html>
<%
	if(conn!=null)conn.close(); //close()를 호출하면 Connection이 Pool에 반환된다. 
%>