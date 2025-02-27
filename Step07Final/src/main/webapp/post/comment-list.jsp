<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="test.post.dao.CommentDao"%>
<%@page import="java.util.List"%>
<%@page import="test.post.dto.CommentDto"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// /post/comment-list.jsp  -> 댓글목록을 json으로 응답할예정
	
	Thread.sleep(1000); //spinner 테스트를 위해 응답시간을 1초 지연시킨다.
	
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
	
	
	//전체 댓글의 갯수
	int totalRow=CommentDao.getInstance().getCount(postNum);
	//전체 페이지의 갯수 구하기
	int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
	
	
	//pageNum에 해당하는 댓글 목록을 얻어낸다.
	List<CommentDto> list = CommentDao.getInstance().getList(dto);
	//Gson 객체에 전달할 Map 객체를 구성한다.
	Map<String, Object> map = new HashMap<>();
	map.put("list", list);
	map.put("totalPageCount", totalPageCount);
	
	Gson gson=new Gson();
	
// [{}, {}, {}] list는 대괄호로/dto는 중괄호로 바꿔서 응답된다.
/* ===> 댓글불러오기때문에 변경 ... 아래같은형태가 나와야함..map을 전달하면 gson이 바꿔줄수있다! //dto를 전달하면 중괄호{}로 바꾸듯이 map도 바꿔준다!
{ "list": [{}, {}, ,,,],
  "totalPageCount":xxx}
*/
%>
<%= gson.toJson(map) %>

