<%@page import="test.food.dao.FoodDao"%>
<%@page import="test.food.dto.FoodDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. 폼 전송되는 종류 이름 가격 추출한다.
	String type = request.getParameter("type");
	String name = request.getParameter("name");
	int price = Integer.parseInt(request.getParameter("price"));

	//2. MemberDto객체에 담는다.
	FoodDto dto = new FoodDto();
	dto.setType(type);
	dto.setName(name);
	dto.setPrice(price);
	//3. DB에 저장한다.
	FoodDao dao = new FoodDao();
	boolean isSuccess=dao.insert(dto);
	//4. 응답하기 (form제출도 요청이니까, 응답해야징)

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>food/insert.jsp</title>
</head>
<body>
	<script>
	<%if(isSuccess){ %>
		alert("<%=name%> 을 추가했습니다.");
		location.href="list.jsp";
	<%}else{ %>
		alert("추가실패!");
		location.href="insertform.jsp";
	<% } %>
	</script>
</body>
</html>