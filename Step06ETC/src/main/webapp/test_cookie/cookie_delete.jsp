<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//삭제할 키값을 가지고 있는 Cookie객체 생성
	Cookie cook = new Cookie("savedMsg", "");
	//Cookie유지시간을 0로 설정하고
	cook.setMaxAge(0);
	//응답하면 Cookie가 강제로 삭제된다.
	response.addCookie(cook);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/test_cookie/cookie_delete.jsp</title>
</head>
<body>
	<p><strong>savedMsg</strong>라는 키값으로 저장된 쿠키를 삭제 했습니다.</p>
	<a href="cookie_read.jsp">쿠키 다시 읽어보기</a>
	<a href="cookie_read2.jsp">read2로 쿠키 다시 읽어보기</a>
</body>
</html>