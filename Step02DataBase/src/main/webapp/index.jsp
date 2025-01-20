<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>index.jsp</title>
<%-- 이 부분은 reousrce.jsp페이지가 응답하도록 한다. --%>
<jsp:include page="/include/resource.jsp"></jsp:include>
</head>
<body class="d-flex flex-column min-vh-100">
	<div class="main flex-grow-1">
		<!-- 출력은 되지만 웹브라우저가 무시하는주석 -->
		<%-- 이 부분은 navbar.jsp페이지가 응답하도록 한다. --%>
		<jsp:include page="/include/navbar.jsp">
			<jsp:param value="index" name="current"/>
		</jsp:include>
		<div class="container">
			<h1>인덱스 페이지 입니다.</h1>
			<ul>
				<li><a href="connection/test.jsp">Connection 테스트</a></li>
				<li><a href="member/list.jsp">회원목록보기</a></li>
				<li><a href="food/list.jsp">맛집목록보기</a></li>
				<li><a href="guest/list.jsp">방명록</a></li>
			</ul>
		</div>
	</div>
	<%-- foot.jsp include --%>
	<jsp:include page="/include/footer.jsp"/>
</body>
</html>