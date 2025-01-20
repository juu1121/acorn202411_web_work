<%@page import="test.food.dto.FoodDto"%>
<%@page import="test.food.dao.FoodDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//GET방식 파라미터로 전달된느 회원번호추출 (updateform.jsp?num=xxx)
	int num = Integer.parseInt(request.getParameter("num"));
	//num에 해당하는 회원정보를 MemberDao객체를 이용해서 얻어온다.
	FoodDto dto = new FoodDao().getData(num);
	//응답한다.
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>food/updateform.jsp</title>
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
					<a href="${pageContext.request.contextPath}/member/list.jsp">음식목록</a>
				</li>
				<li class="breadcrumb-item active">음식수정</li>
			</ol>
		</nav>
		<h1>음식정보 수정폼</h1> 
		
		<p><%=false ? "selected" : "" %></p>
		
		<form action="${pageContext.request.contextPath}/food/update.jsp" method="post">
			<!-- 화면에 보이지는 않지만 폼 제출할때 같이 전송되는 값 -->
			<input type="hidden" name="num" value="<%=dto.getNum()%>"/>
			<div>
				<label class="form-label" for="type">종류</label> 
				<select class="form-control" name="type" id="type">
					<option value="">선택</option>
                	<option <%=dto.getType().equals("한식") ? "selected" : "" %> >한식</option>
                	<option <%=dto.getType().equals("중식") ? "selected" : "" %>>중식</option> 
                	<option <%=dto.getType().equals("양식") ? "selected" : "" %>>양식</option>
                	<option <%=dto.getType().equals("일식") ? "selected" : "" %>>일식</option>
                	<option <%=dto.getType().equals("기타") ? "selected" : "" %>>기타</option>
				</select>
			</div>
			<div>
				<label class="form-label" for="name">이름</label> 
				<input class="form-control" type="text" name="name" id="name" value="<%=dto.getName()%>"/>
			</div>
			<div>
				<label class="form-label" for="price">가격</label> 
				<input class="form-control" type="number" name="price" id="price" max="100000" min="1000" step="100" value="<%=dto.getPrice()%>"/>
			</div>
			<button class="btn btn-warning" type="submit">수정확인</button>
			<button class="btn btn-danger" type="reset">취소</button> 
		</form>
	</div>
</body>
</html>