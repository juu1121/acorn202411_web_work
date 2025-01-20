<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
List<String> names = new ArrayList<>();
names.add("김구라");
names.add("해골");
names.add("원숭이");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>webapp/friends.jsp</title>
</head>
<body>
	<h1>친구목록1</h1>
	<ul>
		<li>
			<%
			out.print(names.get(0));
			%>
		</li>
		<li><%=names.get(1)%></li>
		<li><%=names.get(2)%></li>
	</ul>

	<h1>친구목록2</h1>
	<ul>
		<%
		for (int i = 0; i < names.size(); i++) {
			out.print("<li>" + names.get(i) + "</li>");
		}
		%>
	</ul>

	<h1>친구목록3</h1>
	<ul>
		<%
		for(String tmp : names) { 
			out.print("<li>"+tmp+ "</li>");}
		%>
	</ul>
	<h1>친구목록4</h1>
	<ul>
		<%for(String tmp : names) { %>
			<li><%=tmp%></li>
			<%}%>
	</ul>
	
</body>
</html>