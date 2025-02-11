<%@page import="test.post.dao.CommentDao"%>
<%@page import="test.post.dto.CommentDto"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /post/protected/comment-update.jsp  페이지의 내용 --%>
<%
	// fetch() 로 전송되는 수정할 글 번호와 내용을 읽어와서 
	long num = Long.parseLong(request.getParameter("num"));
	String content = request.getParameter("content");
	//CommentDto 에 담은다음
	CommentDto dto=new CommentDto();
	dto.setNum(num);
	dto.setContent(content);
	//CommentDao 객체를 이용해서 수정반영후 성공여부를 리턴 받는다.
	boolean isSuccess=CommentDao.getInstance().update(dto);
	//json 응답
%>
{"isSuccess":<%=isSuccess %>}

