<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/file/upload.jsp</title>
</head>
<body>
	<div class="container">
		<h1>업로드 결과페이지</h1>
		<p>파일을 업로드 했습니다.</p>
		<%--requestScope.title가 생략되고, 키값으로써도 requestScope영역에서 찾아주는거임 --%>
		<%--req.getAttribute이 아닌 EL을 이용해서 추출 --%>
		<p> title: <strong>${requestScope.title}</strong></p>
		<p>orgFileName : <strong>${orgFileName}</strong></p>
		<p>saveFileName : <strong>${saveFileName }</strong></p>
		<p>fileSize: <strong>${fileSize }</strong> byte</p>
		<p>uploadPath: <strong>${uploadPath }</strong></p>
		<p>	
			<!--get방식 파라미터로 전달 // 다운로드 작업에 필요한 3가지 정보 (원본파일명, 저장된파일명, 파일의 크기)-->
			<a href="${pageContext.request.contextPath}/file/download?orgFileName=${orgFileName}&saveFileName=${saveFileName}&fileSize=${fileSize}">다운로드</a>
		</p>
	</div>
</body>
</html>