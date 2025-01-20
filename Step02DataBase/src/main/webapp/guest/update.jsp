<%@page import="test.guest.dao.GuestDao"%>
<%@page import="test.guest.dto.GuestDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	//1. 폼 전송되는 수정할 회원의 정보를 추출한다.
	int num = Integer.parseInt(request.getParameter("num"));
	String writer = request.getParameter("writer");
	String content = request.getParameter("content");
	String pwd = request.getParameter("pwd");
	String regdate = request.getParameter("regdate");
	
	//GuestDao객체의 참조값
	GuestDao dao = GuestDao.getInstance();
	//DB에 저장된비밀번호
	String savedPwd=dao.getData(num).getPwd();
	//작업의 성공여부를 저장할 변수 만들고 false를 초기값으로 부여한다.
	//if문안에다가 만들면 응답할페이지에서 isSuccess변수를 사용할수없다.
	boolean isSuccess=false;
	//만일 비밀번호가 일치한다면
	if(pwd.equals(savedPwd)){
		//수정할 글 정보를 GuestDto객체에 담고
		//GuestDto dto = new GuestDto(num, writer, content, pwd, regdate);
		GuestDto dto = new GuestDto();
		dto.setNum(num);
		dto.setWriter(writer);
		dto.setContent(content);
		//DB에 수정반영한다
		isSuccess = dao.update(dto);
	}
	
	//3. 응답하기
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/guest/update</title>
</head>
<body>
	<div class="container">
		<h3>알림</h3>
		<%if (isSuccess) {%>
			<p>
				<strong><%=writer %></strong> 님이 작성한 글이 수정되었습니다.
				<a href="list.jsp">목록보기</a>
			</p>
		<%} else {%>
			수정실패
			<a href="updateform.jsp?num=<%=num%>">다시수정</a>
		<%}%>
	</div>

<!--  알림창으로 수정하는 코드!
	<script>
	<%if (isSuccess) {%>
		//웹브라우저가 이걸실행하면 알림창을 띄우고
		alert("수정했습니다");
		//list.jsp페이지로 이동한다.
		location.href="list.jsp";
	<%} else {%>
		//알림창을 띄우고
		alert("수정실패!");
		//updateform.jsp페이지로 이동하면서 num이라는 파라미터명으로 수정할회원의 번호를 가지고간다.
		location.href="updateform.jsp?num=<%=num%>";
	<%}%>
	</script>
-->
</body>
</html>