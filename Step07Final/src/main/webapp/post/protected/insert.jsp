<%@page import="test.user.dto.SessionDto"%>
<%@page import="test.post.dao.PostDao"%>
<%@page import="test.post.dto.PostDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//폼전송되는 title, content 읽어내기
	String title=request.getParameter("title");
	String content=request.getParameter("content");
	//글 작성자 정보 얻어내기
	SessionDto sessionDto=(SessionDto)session.getAttribute("sessionDto");
	
	//글정보를 DB 에 저장하고 
	PostDto dto=new PostDto();
	dto.setTitle(title);
	dto.setContent(content);
	dto.setWriter(sessionDto.getUserName());
	
	boolean isSuccess = PostDao.getInstance().insert(dto);
	request.setAttribute("isSuccess", isSuccess);
	//응답하기
%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/post/protected/create.jsp</title>
</head>
<body>
	<div class="container">
		<c:choose>
			<c:when test="${isSuccess }">
				<p>
					<strong>${sessionDto.userName }</strong> 님이 작성한 글을 저장했습니다.
					<a href="${pageContext.request.contextPath }/post/list.jsp">확인</a>
				</p>
			</c:when>
			<c:otherwise>
				<p>
					글 저장 실패!
					<a href="${pageContext.request.contextPath }/post/protected/new.jsp">다시 작성</a>
				</p>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>







