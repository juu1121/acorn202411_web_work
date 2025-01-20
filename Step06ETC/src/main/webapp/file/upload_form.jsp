<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/file/upload_form.jsp</title>
</head>
<body>
	<%--
		[ 파일 업로드 폼 구성하는 방법 ]
		
		method="post"
		enctype="multipart/form-data"
		<input type="file" >
	 --%>
	 <div class="container">
	 	<h3>파일 업로드 폼</h3>
	 	<%--form안에 파일이있으면, enctype="multipart/form-data" 명시해줘야한다. 
	 	그래야 웹브라우저가 알아서 데이터와 파일을 전송(서버가 알아들을수있게 바이트 알갱이로...등등 알아서!) --%>
	 	<form action="${pageContext.request.contextPath}/file/upload" method="post" 
	 					enctype="multipart/form-data">
	 		<input type="text" name="title" placeholder="설명 입력...">
	 		<br>
	 		<input type="file" name="myFile">
	 		<br>
	 		<button type="submit">업로드</button>
	 	</form>
	 </div>
</body>
</html>