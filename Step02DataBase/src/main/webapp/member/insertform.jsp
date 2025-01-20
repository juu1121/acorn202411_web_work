<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>webapp/member/insertform.jsp</title>
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
					<a href="${pageContext.request.contextPath}/member/list.jsp">회원목록</a>
				</li>
				<li class="breadcrumb-item active">회원추가</li>
			</ol>
		</nav>
		<h1>회원 추가폼</h1>
		<form action="${pageContext.request.contextPath}/member/insert.jsp" method="post">
			<div class="mb-3"> <%--mb-3:마진바텀 3단계 --%>
				<label class="form-label" for="name">이름</label>
				<input class="form-control" type="text" name="name" id="name" placeholder="이름입력..." />
			</div>
			<div class="mb-3">
				<label class="form-label" for="addr">주소</label>
				<input class="form-control" type="text" name="addr" id="addr" placeholder="주소입력..." />
			</div>
			<button class="btn btn-success" type="submit">저장</button>
		</form>
	</div>
</body>
</html>











