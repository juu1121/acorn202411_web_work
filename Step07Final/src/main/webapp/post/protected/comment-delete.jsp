<%@page import="test.post.dao.CommentDao"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /post/protected/comment-delete.jsp 페이지의 내용 --%>
<%
	//삭제할 글번호를 읽어온다.
	long num = Long.parseLong(request.getParameter("num"));
	//CommentDao 객체를 이용해서 삭제하고 성공여부를 응답받는다.
	boolean isSuccess=CommentDao.getInstance().delete(num);
	//성공여부 응답
%>
{"isSuccess":<%=isSuccess%>}