<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//navbar.jsp페이지가 어떤 페이지에 include되었는지 정보 읽어오기
	String currentPage = request.getParameter("current"); // 상단바때문에
%>
	<%-- navbar.jsp에서 응답할내용만 작성 --%>

	<!--navbar-expand-md //md,sm,lg로 작아지는크기를 정할수있다.  -->
	<!--bg-primary로 배경을primary로, data-bs-theme="dark" 배경이 어두우면 글씨색상을 알아서 조정  -->
	
	<nav class="navbar  navbar-expand-md bg-dark" data-bs-theme="dark">
		<div class="container">
		
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">상호명</a>
			<!-- 컨텐츠를 접었다폈다하는 버튼, 뭘 접을지는 #navbarNav 아이디로 참조 -->
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav">
				<span class="navbar-toggler-icon"></span>
			</button> 
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav me-auto">
					<li class="nav-item">
						<a class="nav-link <%=currentPage.equals("member") ? "active" : "" %>" href="${pageContext.request.contextPath}/member/list.jsp">게시판1</a>
					</li>
					<li class="nav-item">
						<a class="nav-link <%=currentPage.equals("food") ? "active" : "" %>" href="${pageContext.request.contextPath}/food/list.jsp">게시판2</a>
					</li>
					<li>
						<a class="nav-link <%=currentPage.equals("guest") ? "active" : "" %>" href="${pageContext.request.contextPath}/guest/list.jsp">게시판3</a>
					</li>
				</ul>
				 <form class="d-flex" role="search">
   				     <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        			<button class="btn btn-outline-light" type="submit">Search</button>
      			</form>
			</div>
			
			
		</div>
	</nav>