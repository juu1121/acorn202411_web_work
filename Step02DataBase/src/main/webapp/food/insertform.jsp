<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>food/insertform</title>
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
				<li class="breadcrumb-item active">음식추가</li>
			</ol>
		</nav>
		<h1>음식 추가폼</h1>
		<form action="${pageContext.request.contextPath}/food/insert.jsp" method="post">
			<div class="mb-3">
				<label calss="form-label" for="type">종류</label>
				<%--<input type="text" name="type" id="type" placeholder="한식, 중식, 일식 입력..." /> --%>
				<select class="form-control" name="type" id="type">
					<%--value를 쓰지않으면 이너텍스트에 적은 문자가 value가 된다! --%>
					<option value="">선택</option>
					<option value="한식">한식</option>
					<option>중식</option>
					<option>양식</option>
					<option>일식</option>
					<option>기타</option>
				</select>
			</div>
			<div class="mb-2">
				<label calss="form-label" for="name">음식이름</label> 
				<input class="form-control" type="text" name="name" id="name" placeholder="음식이름입력..." />
			</div>
			<div class="mb-2">
				<label class="form-label" for="price">음식가격</label> 
				<input class="form-control" type="number" name="price" id="price" max="100000" min="1000" step="100" />
			</div>

			<button class="btn btn-outline-success btn-sm" type="submit">저장</button>
		</form>
	</div>
</body>
</html>