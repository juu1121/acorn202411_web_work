<%@page import="test.food.dao.FoodDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. GET방식 파라미터로 전달되는 삭제할 회원의 번호를 얻어온다.
	int num = Integer.parseInt(request.getParameter("num"));
	//2. MemberDao객체를 이용해서 실제 DB에서 삭제
	new FoodDao().delete(num);
	
	//3. 응답하기
	//특정 경로로 요청을 다시하라는 리다일렉트 응답하기
	//list.jsp => delete.jsp => list.jsp 이런 이동이기 때문에 마치 새로고침하는듯한 느낌을 줄 수있다
	//이거 자체가 응답이기 때문에 html이 필요가없다. 클라이언트한테 가지 않는다!
	
	//context path는 HttpServletRequest객체를 이용해서 얻어낸다음 사용해야한다!
	String cPath = request.getContextPath();
	response.sendRedirect(cPath+"/food/list.jsp"); 
	
	
%>
