<%@page import="test.user.dto.UserDto"%>
<%@page import="test.user.dao.UserDao"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. GET 방식 파라미터로 전달되는 입력한 id 값을 읽어온다.
	String userName = request.getParameter("userName");
	//2. DB에서 해당 회워정보가 있는지 확인해서 사용가능 여부를 알아낸다.
	UserDto dto = UserDao.getInstance().getData(userName); //ID를 얻어왔을때 ->select가되면 이미 존재, null이어야 사용가능한것
	boolean canUse = dto == null ? true : false;
	
%>
{"canUse":<%=canUse%>}   

<%--응답되는 json문자열은  {canUse:true} or {canUse:false } --%>
