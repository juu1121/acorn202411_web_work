<%@page import="test.user.dto.SessionDto"%>
<%@page import="test.post.dao.PostDao"%>
<%@page import="test.post.dto.PostDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//검색조건이 있는지 읽어와 본다.
	String condition=request.getParameter("condition");
	String keyword=request.getParameter("keyword");
	String findQuery=null;
	//있다면 dto 에 해당 정보를 담는다.
	PostDto findDto=new PostDto();
	if(condition != null){
		//findDto=new PostDto();
		findDto.setCondition(condition);
		findDto.setKeyword(keyword);
		findQuery="&condition="+condition+"&keyword="+keyword;
	}

	//자세히 보여줄 글의 번호를 읽어온다. 
	int num=Integer.parseInt(request.getParameter("num"));
	findDto.setNum(num);
	
	//DB 에서 해당 글의 정보를 얻어와서 
	PostDto dto=PostDao.getInstance().getData(findDto);
	
	//세션 아이디를 읽어와서 
	String sessionId=session.getId();
	//이미 읽었는지 여부를 얻어낸다 
	boolean isReaded=PostDao.getInstance().isReaded(num, sessionId);
	if(!isReaded){
		//글 조회수도 1 증가 시킨다
		PostDao.getInstance().addViewCount(num);
		//이미 읽었다고 표시한다. 
		PostDao.getInstance().insertReaded(num, sessionId);
	}
	
	request.setAttribute("dto", dto);
	request.setAttribute("findDto", findDto);
	request.setAttribute("findQuery", findQuery);
	//응답한다 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/post/view.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<style>
    #contents {
        margin-top: 20px;
        padding: 20px;
        background-color: #fefefe;
        border-radius: 10px;
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
        border: 1px solid #ddd;
        transition: transform 0.3s ease, box-shadow 0.3s ease;
    }
    #contents:hover {
        transform: translateY(-5px);
        box-shadow: 0 15px 30px rgba(0, 0, 0, 0.3);
    }
    
    #content{
    	width: 100%;
    	height: 300px;
    }
</style>
</head>
<body>
	<div class="container">
		<c:if test="${dto.prevNum ne 0}">
			<a href="view.jsp?num=${dto.prevNum}${findQuery}">이전글</a>
		</c:if>
		<c:if test="${dto.nextNum ne 0}">
			<a href="view.jsp?num=${dto.nextNum}${findQuery}">다음글</a>
		</c:if>
		<c:if test="${not empty findDto.condition}">
			<p>
				<strong>${findDto.condition }</strong> 조건
				<strong>${findDto.keyword }</strong>검색어로 검색된 내용 자세히보기
			</p>
		</c:if>
		<h3>글 상세 보기</h3>
		<table class="table table-bordered">
			<tr>
				<th>글번호</th>
				<td>${dto.num }</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${dto.writer }</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${dto.title }</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${dto.viewCount }</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${dto.createdAt }</td>
			</tr>
			<tr>
				<th>수정일</th>
				<td>${dto.updatedAt }</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="contents">${dto.content }</div>
				</td>
			</tr>
		</table>
		
		<%-- 만일 글 작성자가 로그인된 아이디와 같다면 수정, 삭제 링크를 제공한다. --%>
		<c:if test="${dto.writer eq sessionDto.userName }">
			<a class="btn btn-outline-success btn-sm" href="${pageContext.request.contextPath }/post/protected/edit.jsp?num=${dto.num }">수정</a>
			<a class="btn btn-outline-danger btn-sm" href="javascript:" onclick="deleteConfirm()">삭제</a>
			<script>
				function deleteConfirm(){
					const isDelete=confirm("이 글을 삭제 하겠습니까?");
					if(isDelete){
						//javascript 를 이용해서 페이지 이동 시키기
						location.href="${pageContext.request.contextPath }/post/protected/delete.jsp?num=${dto.num}";
					}
				}
			</script>
		</c:if>
		
	</div>
</body>
</html>