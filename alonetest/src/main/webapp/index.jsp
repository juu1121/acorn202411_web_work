<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">


<title>네비바 푸터</title>
<%--포토라마 --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/include/jquery_plugin/css/fotorama.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"> </script>
    <script src="${pageContext.request.contextPath}/include/jquery_plugin/js/fotorama.js"></script>
    
<%-- 부트스트랩로딩 --%>
<jsp:include page="/include/resource.jsp"></jsp:include>
>>>>>>> branch 'master' of https://github.com/juu1121/acorn202411_web_work.git
</head>


<body class="d-flex flex-column min-vh-100">
	<div class="main flex-grow-1"> <%-- 푸터를 제외한 내용을 감싼div --%>
	
		<%-- 네비바 --%>
		<jsp:include page="/include/navbar.jsp">
			<jsp:param value="index" name="current"/>
		</jsp:include>
		
		<div class="container" >
			<h1>이미지 슬라이더</h1>
			 <div class="d-flex justify-content-center">
				<div class="fotorama" data-allowfullscreen="true"  data-autoplay="true" data-keyboard="true"  data-nav="thumbs"  >
					<img src="include/img/top06.jpg" />
					<img src="include/img/top07.jpg" />
					<img src="include/img/top08.jpg" />
				</div>
			</div>
		</div>
			
	</div>
	
	<%-- 푸터 --%>
	<jsp:include page="/include/footer.jsp"/>

</body>
</html>