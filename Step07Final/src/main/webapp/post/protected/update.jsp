<%@page import="test.post.dao.PostDao"%>
<%@page import="test.post.dto.PostDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//수정할 글의 정보를 읽어온다.
	int num=Integer.parseInt(request.getParameter("num"));
	String title=request.getParameter("title");
	String content=request.getParameter("content");
	//DB 에 수정반영
	PostDto dto=new PostDto();
	dto.setNum(num);
	dto.setTitle(title);
	dto.setContent(content);
	boolean isSuccess=PostDao.getInstance().update(dto);
	request.setAttribute("isSuccess", isSuccess);
	//응답
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/post/protected/update.jsp</title>
</head>
<body>
	<c:choose>
		<c:when test="${isSuccess }">
			<script>
				alert("수정 했습니다");
				location.href="${pageContext.request.contextPath }/post/view.jsp?num=${param.num}";
			</script>
		</c:when>
		<c:otherwise>
			<script>
				alert("수정실패!");
				location.href="${pageContext.request.contextPath }/post/protected/edit.jsp?num=${param.num}";
			</script>
		</c:otherwise>
	</c:choose>
</body>
</html>









