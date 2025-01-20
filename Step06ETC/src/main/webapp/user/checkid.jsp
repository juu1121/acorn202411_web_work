<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. GET 방식 파라미터로 전달되는 입력한 id 값을 읽어온다.
	String id = request.getParameter("id");
	//2. DB에서 해당 회워정보가 있는지 확인해서 사용가능 여부를 알아낸다.
	boolean canUse=false;
	//"kimgura"라는 아이디는 이미 등록되어있다고 생각하면된다.
	if(!id.equals("kimgura")){
		canUse=true;
	}
	
%>
{"canUse":<%=canUse%>}   

<%--응답되는 json문자열은  {canUse:true} or {canUse:false } --%>
