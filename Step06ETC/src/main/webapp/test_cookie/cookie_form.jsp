<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/test_cookie/cookie_form.jsp</title>
</head>
<body>
	<div class="container">
		<h1>쿠키 테스트</h1>
		<p>
			클라이언트의 웹브라우저에 특정 key값으로 문자열을 저장할수있다.
			저장할수있는 문자열은 Base64인코딩 형식의 64가지 문자열을 저장할수있다.
		</p>
		<form action="cookie_save.jsp" method="post">
			<label for="msg">웹브라우저(client side)에 저장할 문자열</label>
			<input type="text" name="msg" id="msg" />
			<button type="submit">저장</button>
		</form>
	</div>
</body>
</html>