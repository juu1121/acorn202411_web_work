<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//테스트를 위해 sample데이터를 request scope에 담는다.
	List<String> names=new ArrayList<String>();
	names.add("김구라");
	names.add("해골");
	names.add("원숭이");
	//"list"라는 키값으로 request scope에 ArrayList 객체 담아두기
	request.setAttribute("list", names);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/jstl/test01.jsp</title>
</head>
<body>
	<h1>친구목록</h1>
	<%
		//request 영역에 "list"라는 키값으로 담긴 친구목록을 얻어와서 원래 type으로 캐스팅
		List<String> list = (List<String>)request.getAttribute("list"); //리턴되는건 오브젝트타입 so, 워래타입으로 캐스팅해야함!
	%>
	<ul>
		<%for(String tmp:list){ %>
			<li><%=tmp %></li>
		<%} %>
	</ul>
	
	<h1>친구 목록_EL과 JSTL활용</h1>
	<ul>
		<c:forEach var="tmp" items="${requestScope.list}">  
			<li>${tmp }</li>
		</c:forEach>
	</ul>
	
	<h1>친구 목록 인덱스 표시</h1>
	<ul>
		<c:forEach var="tmp" items="${list }" varStatus="status">
			<li>${tmp } <strong>인덱스 : ${status.index }</strong></li>
		</c:forEach>
	</ul>
	
	<h1>친구 목록 순서 표시</h1>
	<ul>
		<c:forEach var="tmp" items="${requestScope.list }" varStatus="status">
			<li>${tmp } <strong>순서 : ${status.count }</strong></li>
		</c:forEach>
	</ul>
	
	<h1>친구 목록 첫번째 순서인지 여부</h1>
	<ul>
		<c:forEach var="tmp" items="${requestScope.list }" varStatus="status">
			<li>${tmp } <strong>첫번째 : ${status.first }</strong></li>
		</c:forEach>
	</ul>
	
		<h1>친구 목록 마지막 순서인지 여부</h1>
	<ul>
		<c:forEach var="tmp" items="${requestScope.list }" varStatus="status">
			<li>${tmp } <strong>마지막번째 : ${status.last }</strong></li>
		</c:forEach>
	</ul>
</body>
</html>