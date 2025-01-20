<%@page import="test.guest.dao.GuestDao"%>
<%@page import="test.guest.dto.GuestDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. 수정할 글번호를 읽어온다.
	int num=Integer.parseInt(request.getParameter("num"));
	//2. 글번호에 해당하는 글정보를 DB 에서 얻어온다.
	GuestDto dto = GuestDao.getInstance().getData(num);
	//3. 글 수정 양식을 응답한다. 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/guest/updateform.jsp</title>
<jsp:include page="/include/resource.jsp"></jsp:include>
</head>
<body>
	<div class="container">
		<nav>
			<ol class="breadcrumb">
				<li class="breadcrumb-item">
					<a href="${pageContext.request.contextPath}/">HOME</a>
				</li>
				<li class="breadcrumb-item">
					<a href="${pageContext.request.contextPath}/guest/list.jsp">방명록 목록</a>
				</li>
				<li class="breadcrumb-item active">글수정</li>
			</ol>
		</nav>
		<h1>방명록 글 수정 폼</h1>
		<form action="update.jsp" method="post">
			<div class="mb-2">
				<label class="form-label" for="num">번호</label>
				<input class="form-control" type="text" name="num" id="num" 
					value="<%=dto.getNum() %>" readonly/>
			</div>
			<div class="mb-2">
				<label class="form-label" for="writer">작성자</label>
				<input class="form-control" type="text" name="writer" id="writer" 
					value="<%=dto.getWriter() %>"/>
			</div>
			<div class="mb-2">
				<label class="form-label" for="content">내용</label>
				<textarea class="form-control" name="content" id="content" style="height:200px"><%=dto.getContent() %></textarea>
			</div>
			<div class="mb-2">
				<label class="form-label" for="pwd">글 작성시 입력했던 비밀번호</label>
				<input class="form-control" type="text" name="pwd" id="pwd"/>
			</div>
			<button type="submit" class="btn btn-outline-success btn-sm">수정확인</button>
			<button type="reset" class="btn btn-outline-danger btn-sm">취소</button>
		</form>
	</div>
</body>
</html>













