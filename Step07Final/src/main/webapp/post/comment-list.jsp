<%@page import="com.google.gson.Gson"%>
<%@page import="test.post.dao.CommentDao"%>
<%@page import="java.util.List"%>
<%@page import="test.post.dto.CommentDto"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// /post/comment-list.jsp  -> 댓글목록을 json으로 응답할예정
	
	//댓글의 페이지 번호
	int pageNum=Integer.parseInt(request.getParameter("pageNum"));
	//원글의 글번호
	long postNum=Long.parseLong(request.getParameter("postNum"));
	
	// 이 dto에는 페이지넘, 포스트넘, 스타트로우넘, 앤드로우넘을 담아야한다.
	CommentDto dto = new CommentDto();
	dto.setPostNum(postNum);
	
	/*
	[ 댓글 페이징 처리에 관련된 로직 ] pageNum을 이용해 startRowNum과 엔드로우넘을 계산함
	*/
	//한 페이지에 댓글을 몇개씩 표시할 것인지
	final int PAGE_ROW_COUNT=10;
	
	//보여줄 페이지의 시작 ROWNUM
	int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
	//보여줄 페이지의 끝 ROWNUM
	int endRowNum=pageNum*PAGE_ROW_COUNT;
	//계산된 값을 dto 에 담는다
	dto.setStartRowNum(startRowNum);
	dto.setEndRowNum(endRowNum);
	
	//pageNum에 해당하는 댓글 목록을 얻어낸다.
	List<CommentDto> list = CommentDao.getInstance().getList(dto);
	Gson gson=new Gson();
	
// [{}, {}, {}] list는 대괄호로/dto는 중괄호로 바꿔서 응답된다.
%>
<%= gson.toJson(list) %>

