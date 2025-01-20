<%@page import="test.food.dao.FoodDao"%>
<%@page import="test.food.dto.FoodDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. 폼 전송되는 수정할 맛집의 번호, 종류 이름 가격을 추출한다.
	int num = Integer.parseInt(request.getParameter("num"));
	String type = request.getParameter("type");
	String name = request.getParameter("name");
	int price = Integer.parseInt(request.getParameter("price"));
	//2. DB에 수정반영한다.
	FoodDto dto = new FoodDto(num, type, name, price);
	boolean isSuccess = new FoodDao().update(dto);
	//3. 응답하기

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>food/update.jsp</title>
</head>
<body>
	

	<script>		
	<%if (isSuccess) {%>
		//웹브라우저가 이걸실행하면 알림창을 띄우고
		alert("수정했습니다");
		//list.jsp페이지로 이동한다.
		location.href="list.jsp";
	<%} else {%>
		//알림창을 띄우고
		alert("수정실패!");
		//updateform.jsp페이지로 이동하면서 num이라는 파라미터명으로 수정할회원의 번호를 가지고간다.
		location.href="updateform.jsp?num=<%=num%>";
	<%}%>

	</script>
</body>
</html>