<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//navbar.jsp페이지가 어떤 페이지에 include되었는지 정보 읽어오기
	String currentPage = request.getParameter("current"); // "index" or "member" or "food"
%>
	<%-- navbar.jsp에서 응답할내용만 작성 --%>

	<!--navbar-expand-md //md,sm,lg로 작아지는크기를 정할수있다.  -->
	<!--bg-primary로 배경을primary로, data-bs-theme="dark" 배경이 어두우면 글씨색상을 알아서 조정  -->
	<nav class="navbar navbar-expand-md bg-primary" data-bs-theme="dark">
		<div class="container">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">Acorn</a>
			<!-- 컨텐츠를 접었다폈다하는 버튼, 뭘 접을지는 #navbarNav 아이디로 참조 -->
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button> 
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item">
						<a class="nav-link <%=currentPage.equals("member") ? "active" : "" %>" href="${pageContext.request.contextPath}/member/list.jsp">Member</a>
					</li>
					<li class="nav-item">
						<a class="nav-link <%=currentPage.equals("food") ? "active" : "" %>" href="${pageContext.request.contextPath}/food/list.jsp">Food</a>
					</li>
					<li>
						<a class="nav-link <%=currentPage.equals("guest") ? "active" : "" %>" href="${pageContext.request.contextPath}/guest/list.jsp">방명록</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>