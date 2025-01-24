<%@page import="java.util.List"%>
<%@page import="test.post.dto.PostDto"%>
<%@page import="test.post.dao.PostDao"%>
<%@page import="oracle.jdbc.proxy.annotation.Post"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//한 페이지에 게시물을 몇개씩 표시할 것인지
	final int PAGE_ROW_COUNT=5;
	//하단 페이지를 몇개씩 표시할 것인지
	final int PAGE_DISPLAY_COUNT=5;
	
	//보여줄 페이지의 번호를 일단 1이라고 초기값 지정
	int pageNum=1;
	
	//페이지 번호가 파라미터로 전달되는지 읽어와 본다.
	//get방식 파라미터로 list.jsp or list.jsp?pageNum=2  이런식으로 넘어올거
	String strPageNum=request.getParameter("pageNum");  
	//만일 페이지 번호가 파라미터로 넘어 온다면
	if(strPageNum != null){
		//숫자로 바꿔서 보여줄 페이지 번호로 지정한다.
		pageNum=Integer.parseInt(strPageNum);
	}	
	
	//보여줄 페이지의 시작 ROWNUM
	int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
	//보여줄 페이지의 끝 ROWNUM
	int endRowNum=pageNum*PAGE_ROW_COUNT;
	
	//하단 시작 페이지 번호 
	int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
	//하단 끝 페이지 번호
	int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
	//전체 글의 갯수
	int totalRow=PostDao.getInstance().getCount();
	//전체 페이지의 갯수 구하기 //하단 페이지이동의 UI를 몇까지 출력할건지 알기위해서 전체페이지도 필요
	int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
	//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
	if(endPageNum > totalPageCount){
		endPageNum=totalPageCount; //보정해 준다. 
	}	
	
	// startRowNum 과 endRowNum 을 PostDto 객체에 담아서
	PostDto dto=new PostDto();
	dto.setStartRowNum(startRowNum);
	dto.setEndRowNum(endRowNum);
	//보여줄 페이지에 해당하는 글 목록을 얻어온다. //jstl과 el을 사용하기위해서
	List<PostDto> list=PostDao.getInstance().getList(dto);
	request.setAttribute("list", list);
	request.setAttribute("startPageNum", startPageNum);
	request.setAttribute("endPageNum", endPageNum);
	request.setAttribute("totalPageCount", totalPageCount);
	request.setAttribute("pageNum", pageNum);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/post/list.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<a href="${pageContext.request.contextPath }/post/protected/new.jsp">새글 작성</a>
		<h1>게시글 목록 입니다</h1>
		<table class="table table-striped">
			<thead class="table-dark">
				<tr>
					<th>번호</th>
					<th>작성자</th>
					<th>제목</th>
					<th>조회수</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="post" items="${list}">
					<tr>
						<td>${post.num}</td>
						<td>${post.writer}</td>
						<td>
							<a href="view.jsp?num=${post.num}">${post.title}</a>
						</td>
						<td>${post.viewCount}</td>
						<td>${post.createdAt}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<nav>
			<ul class="pagination">
				<!-- Prev 버튼 --> <!-- 이전 다음페이지를 만들지 ex)1페이지의 이전은 없잖앙 -->
				<c:if test="${startPageNum ne 1}">
					<li class="page-item">
						<a class="page-link" href="list.jsp?pageNum=${startPageNum - 1}">Prev</a>
					</li>
				</c:if>
				<!-- 페이지 번호 -->
				<c:forEach begin="${startPageNum}" end="${endPageNum}" var="i">
					<%--현재페이지 진하게 나오려고 active --%>
					<li class="page-item ${i == pageNum ? 'active' : ''}">     
						<a class="page-link" href="list.jsp?pageNum=${i}">${i}</a>
					</li>
				</c:forEach>
				<!-- Next 버튼 -->
				<c:if test="${endPageNum < totalPageCount}">
					<li class="page-item">
						<a class="page-link" href="list.jsp?pageNum=${endPageNum + 1}">Next</a>
					</li>
				</c:if>
			</ul>		
		</nav>		
	</div>
</body>
</html>