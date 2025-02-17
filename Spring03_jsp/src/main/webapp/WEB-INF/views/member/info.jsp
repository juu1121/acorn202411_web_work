<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/member/info.jsp</title>
</head>
<body>
	<div class="container">
		<p> 번호 : <strong>${dto.num }</strong></p>
		<p> 이름 : <strong>${dto.name }</strong></p>
		<p> 주소 : <strong>${dto.addr }</strong></p>
	</div>
</body>
</html>