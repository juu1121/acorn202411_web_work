<%@page import="test.guest.dto.GuestDto"%>
<%@page import="test.guest.dao.GuestDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//방명록 글 목록을 얻어온다.
	List<GuestDto> list = GuestDao.getInstance().getList();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/guest/list.jsp</title>
<jsp:include page="/include/resource.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/include/navbar.jsp">
		<jsp:param value="guest" name="current"/>
	</jsp:include>
	<div class="container">
		<a href="insertform.jsp">새 글 작성</a>
		<h1>방명록 목록입니다.</h1>
		<table class="table table-bordered">
			<thead class="table-dark">
				<tr>
					<th>번호</th>
					<th>작성자</th>
					<th>내용</th>
					<th>등록일</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
			<%for(GuestDto tmp:list){ %>
				<tr>
					<td><%=tmp.getNum() %></td>
					<td><%=tmp.getWriter() %></td>
					<td>
						<textarea class="form-control" style="height:100px;resize:none;" readonly><%=tmp.getContent() %></textarea>
					</td>
					<td><%=tmp.getRegdate() %></td>
					<td>
						<a href="updateform.jsp?num=<%=tmp.getNum()%>">수정</a>
					</td>
					<td>
						<form action="delete.jsp" method="post">
							<input type="hidden" name="num" value="<%=tmp.getNum() %>" />
							<input type="text" name="pwd" placeholder="비밀번호..." />
							<button class="btn btn-danger btn-sm" type="submit">삭제</button>
						</form>
					</td>
				</tr>
				<%} %>
			</tbody>
		</table>
	</div>
</body>
</html>