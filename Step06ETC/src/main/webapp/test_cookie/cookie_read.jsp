<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//HttpServletRequest객체를 이용해서 요청과 함께 전달된 쿠키 읽어내기
	//쿠키는 request객체로 읽어낼수있음
	Cookie[] cooks=request.getCookies();
	
	//반복문돌면서
	for(Cookie tmp:cooks){
		//쿠키의 키값을 읽어온다.
		String key=tmp.getName();
		//해당 키값으로 저장된 value값을 읽어온다.
		String value = tmp.getValue();
		
		System.out.println("key:"+key+" vale:"+value);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/tet_cookie/cookie_read.jsp</title>
</head>
<body>
	<div class="container">
		<h1>저장된 쿠키 목록</h1>
		<table>
			<thead>
				<tr>
					<th>key</th>
					<th>value</th>
				</tr>
			</thead>
			<tbody>
			<%for(Cookie tmp:cooks){ %>
				<tr>
					<td><%=tmp.getName() %></td>
					<td><%=tmp.getValue() %></td>
				</tr>
			<%} %>
			</tbody>
		</table>
		<a href="cookie_form.jsp">다시 테스트 </a>
		
	</div>
</body>
</html>