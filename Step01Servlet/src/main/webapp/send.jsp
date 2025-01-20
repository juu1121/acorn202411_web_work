<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 

	/*
		여기는 서블릿의 service()메소드 안쪽 영역이라고 생각
	*/

	String fortuneToday = "서쪽으로가면 강남역을 만나요!";
	//요청 파라미터추출
	String msg = request.getParameter("msg");
	//콘솔에 출력
	System.out.println(msg);
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>webapp/send.jsp</title>
</head>
<body>
	<h1>나는 jsp페이지!</h1>
	<p>오늘의 운세 : <strong> <%out.print(fortuneToday); %></strong></p>
	<p>오늘의 운세 : <strong><%=fortuneToday %></strong></p> 
	<%for (int i=0; i<3; i++){ %>
			<p><%=i %></p>
		<%} %>
</body>
</html>