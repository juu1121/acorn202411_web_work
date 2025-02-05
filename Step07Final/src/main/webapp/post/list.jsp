<%@page import="java.util.List"%>
<%@page import="test.post.dto.PostDto"%>
<%@page import="test.post.dao.PostDao"%>
<%@page import="oracle.jdbc.proxy.annotation.Post"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//검색조건이 있는지 읽어와 본다.
	String condition=request.getParameter("condition");
	String keyword=request.getParameter("keyword");
	String findQuery=null;
	//검색조건이없다면, findQuery가 null인 상태로 if문 건너뛰고 아래쪽으로 내려간다!
	//있다면 dto 에 해당 정보를 담는다. //즉, 검색조건과 키워드를 있고없고에 따라 dto에 선택적으로 담을수도있고, 안 담을수도있다.
	PostDto dto=new PostDto();
	if(condition != null){
		dto.setCondition(condition);
		dto.setKeyword(keyword);
		findQuery="&condition="+condition+"&keyword="+keyword;
	}
	
	//한 페이지에 몇개씩 표시할 것인지
	final int PAGE_ROW_COUNT=5;
	//하단 페이지를 몇개씩 표시할 것인지
	final int PAGE_DISPLAY_COUNT=5;
	
	//보여줄 페이지의 번호를 일단 1이라고 초기값 지정
	int pageNum=1;
	
	//페이지 번호가 파라미터로 전달되는지 읽어와 본다.
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
	int totalRow=PostDao.getInstance().getCount(dto);
	//전체 페이지의 갯수 구하기
	int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
	//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
	if(endPageNum > totalPageCount){
		endPageNum=totalPageCount; //보정해 준다. 
	}	
	
	// startRowNum 과 endRowNum 을 PostDto 객체에 담아서
	dto.setStartRowNum(startRowNum);
	dto.setEndRowNum(endRowNum);
	
	
	//보여줄 페이지에 해당하는 글 목록을 얻어온다.
	List<PostDto> list=PostDao.getInstance().getList(dto); //검색조건이 들어있을수도 없을수도있는 dto를 메소드에 넘겨주면서 글 목록을 얻어낸다.
	/*
		jsp페이지에서 응답에 필요한 데이터를 el에서 활용할수있도록 request객체에 특정 키값으로 담는다.
		여기서 담아야 EL을 활용할수있어 // 어떤 key값으로 어떤 type을 담았는지 기억하기!
	*/
	request.setAttribute("list", list); //List<PostDto>
	request.setAttribute("startPageNum", startPageNum);
	request.setAttribute("endPageNum", endPageNum);
	request.setAttribute("totalPageCount", totalPageCount);
	request.setAttribute("pageNum", pageNum);
	request.setAttribute("totalRow", totalRow);
	request.setAttribute("dto", dto);
	request.setAttribute("findQuery", findQuery);
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
		<a href="${pageContext.request.contextPath }/post/protected/new2.jsp">새글작성_새로운데이터시도</a>
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
				<%--list는 //List<PostDto> --%>
				<c:forEach var="post" items="${list}"> 
					<tr>
						<td>${post.num}</td>
						<td>${post.writer}</td>
						<td>
							<a href="view.jsp?num=${post.num}${findQuery}">${post.title}</a>
						</td>
						<td>${post.viewCount}</td>
						<td>${post.createdAt}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<nav>
			<ul class="pagination">
				<!-- Prev 버튼 -->
				<c:if test="${startPageNum ne 1}">
					<li class="page-item">
						<a class="page-link" href="list.jsp?pageNum=${startPageNum - 1}${findQuery}">Prev</a>
					</li>
				</c:if>
				<!-- 페이지 번호 -->
				<c:forEach begin="${startPageNum}" end="${endPageNum}" var="i">
					<li class="page-item ${i == pageNum ? 'active' : ''}">
						<a class="page-link" href="list.jsp?pageNum=${i}${findQuery}">${i}</a> 
						<%--el의 특징: 값이 null이면 아무것도 안 찍는다. // findQuery는 없을수도있고 있을수도있다.--%>
					</li>
				</c:forEach>
				<!-- Next 버튼 -->
				<c:if test="${endPageNum < totalPageCount}">
					<li class="page-item">
						<a class="page-link" href="list.jsp?pageNum=${endPageNum + 1}${findQuery}">Next</a>
					</li>
				</c:if>
			</ul>		
		</nav>
        <form action="${pageContext.request.contextPath }/post/list.jsp" method="get">
        	<label for="condition">검색조건</label>
        	<select name="condition" id="condition">
        		<option value="title_content" ${dto.condition eq 'title_content' ? 'selected' : ''}>제목 + 내용</option>
        		<option value="title" ${dto.condition eq 'title' ? 'selected' : ''}>제목</option>
        		<option value="writer" ${dto.condition eq 'writer' ? 'selected' : ''}>작성자</option>
        	</select>
        	<input type="text" name="keyword" placeholder="검색어..." value="${dto.keyword }"/>
        	<button class="btn btn-primary btn-sm" type="submit">검색</button>
        	<a class="btn btn-success btn-sm" href="${pageContext.request.contextPath }/post/list.jsp">새로고침</a>
        </form>
        <c:if test="${not empty dto.keyword }">
        	<p>
        		<strong>${totalRow }</strong> 개의 자료가 검색 되었습니다.
        	</p>
        </c:if>				
	</div>
</body>
</html>