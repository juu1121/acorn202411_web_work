<%@page import="test.member.dto.MemberDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//request scope에 "list"라는 키값으로 담겨있는 List객체를 얻어낸다.
	List<MemberDto> list = (List<MemberDto>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/list.jsp</title>
</head>
<body>
	<div class="container">
		<h1>회원목록입니다.</h1>
		<table>
			<thead>
				<tr>
					<th>번호</th>
					<th>이름</th>
					<th>주소</th>
				</tr>
			</thead>
			<tbody>
			<%--EL에는 for문이 없다 EL로 출력하려면, for문의 형태를 jstl로 해야함 --%>
			<% for(MemberDto tmp:list){%>
				<tr>
					<td><%=tmp.getNum()%></td>
					<td><%=tmp.getName()%></td>
					<td><%=tmp.getAddr()%></td>
				</tr>	
			<%}%>
			</tbody>
		</table>
	</div>
</body>
</html>