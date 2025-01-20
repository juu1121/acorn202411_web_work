<%@page import="test.member.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//request.getAttribute("dto"); dto라는 키값을 읽어오면 오브젝트로 리턴 = 원래타입으로 캐스팅해줌
MemberDto dto=(MemberDto)request.getAttribute("dto");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<h1>회원정보 1번</h1>
		<p>회원번호 : <strong><%=dto.getNum() %></strong></p>
		<p>회원이름 : <strong><%=dto.getName() %></strong></p>
		<p>회원주소 : <strong><%=dto.getAddr() %></strong></p>
		
		<h1>회원정보 2번EL사용</h1>
		<%--EL을 이용해서 requestScope에 담긴 내용을 추출할수있다. --%>
		<p>회원번호 : <string> ${requestScope.dto.getNum()} </string></p>
		<%--requestScope.은 생략가능 --%>
		<p>회원이름 : <string> ${dto.getName()} </string></p>
		<%--.필드명만 명시해도 getter메소드가 자동호출 --%>
		<p>회원주소 : <string> ${dto.addr} </string></p>
	</div>
</body>
</html>