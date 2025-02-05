<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index.jsp</title>
</head>
<body>
	<div class="container">
		<c:choose>
			<c:when test="${empty sessionScope.sessionDto}">
				<p>
					<a href="${pageContext.request.contextPath }/user/login-form.jsp">로그인</a>
					<a href="${pageContext.request.contextPath }/user/signup-form.jsp">회원가입</a>
				</p>
			</c:when>
			<c:otherwise>
				<p>	
					<%--개인정보(수정, 탈퇴등은 protected에 만들거임 ) --%>
					<a href="${pageContext.request.contextPath}/user/protected/info.jsp">${sessionDto.userName }</a>님 로그인중...
					<a href="${pageContext.request.contextPath }/user/logout.jsp">로그아웃</a>
				</p>
			</c:otherwise>
		</c:choose>
		
		<h1>인덱스 페이지 입니다.</h1>
		<ul>
			<li><a href="${pageContext.request.contextPath}/jstl/hello.jsp">JSTL 테스트</a></li>
			<li><a href="${pageContext.request.contextPath}/user/signup-form.jsp">회원가입</a></li>
			<li><a href="${pageContext.request.contextPath}/user/login-form.jsp">로그인</a></li>
			<li><a href="${pageContext.request.contextPath}/member-only/play.jsp">놀러가기</a></li>
			<li><a href="${pageContext.request.contextPath}/post/list.jsp">글 목록보기</a></li>
			<li><a href="toastui/form.jsp">Toast UI Editor 테스트</a></li>
		</ul>
	</div>
</body>
</html>