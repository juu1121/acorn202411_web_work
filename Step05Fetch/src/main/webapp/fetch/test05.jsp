<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test05.jsp</title>
</head>
<body>
	<h1>폼에 입력한 내용을 페이지 전환없이 전송하기</h1>
	<form action="${pageContext.request.contextPath}/user/login.jsp" method="post" id="myForm">
		<input type="text" name="id" placeholder="아이디입력..." />
		<input type="password" name = "pwd" placeholder="비밀번호입력..." />
		<button type="submit">로그인</button>
	</form>
	
	<script>
		//폼에 submit 이벤트가 발생했을때 실행할 함수 등록
		//폼에 submit이벤트가 발생하면 매게변수에 이벤트객체전달!
		document.querySelector("#myForm").addEventListener("submit", (e)=>{
			//event객체의 .preventDefault()함수를 호출하면 기본동작을 막는다.(폼제출이 막아진다.)
			e.preventDefault();
			console.log("submit");
			//폼에 입력한 내용을 FormData type으로 얻어내기
			//FormData는 폼의입력한 값을 data객체로 담는거?
			const data = new FormData(e.target); //e.target은 form의 참조값이다.
			//FormData를 이용해서  query string 얻어내기
			const queryString = new URLSearchParams(data).toString(); //"name=John&age=30"
			//query string을 콘솔에 출력해보기
			console.log(queryString);
			//action속성의 value 읽어오기
			const url = e.target.action;
			//fetch() 함수를 이용해서 post 방식 요청하면서 queryString을 요청의 body에 전달하기
			fetch(url, {
				method:"POST",
				headers:{"Content-Type":"application/x-www-form-urlencoded; charset=utf-8"},
				body:queryString //위에서 화면전환을 막았잖아! post로전송할내용을 바디에 담아서전송!
			})
			.then(res=>res.json())
			.then(data=>{
				//data는 object이고 id와 pwd의 유효성 여부가 담겨있따.
				console.log(data);
			})
			.catch(error=>{
				console.log("에러정보: "+error);
			});
		});
		
		

	</script>
</body>
</html>