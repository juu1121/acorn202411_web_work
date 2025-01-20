
<%@page import="test.member.dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//MemberDto 객체를 생성해서
	MemberDto dto=new MemberDto(1, "김구라", "노량진");	
	
	//"dto" 라는 키값으로 request scope 에 담기
	request.setAttribute("dto", dto);
%>         
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/test_el/test03.jsp</title>
</head>
<body>
	<h3>EL 을 이용해서 request 영역에 담긴 내용을 추출할수 있다.</h3>
	<p>번호 : ${dto.getNum() }</p>
	<p>이름 : ${dto.getName() }</p>
	<p>주소 : ${dto.getAddr() }</p>
	
	<hr>
	
	<h3> getter 메소드 대신에 필드명만 적어도 된다. </h3>
	<p>번호 : ${dto.num }</p>
	<p>이름 : ${dto.name }</p>
	<p>주소 : ${dto.addr }</p>
</body>
</html>


















