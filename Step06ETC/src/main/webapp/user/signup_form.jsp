<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/user/signup_form.jsp</title>
<style>
	.invalid-feedback{
		display:none;
		color: red; 
	}
</style>
</head>
<body>
	<div class="container">
		<h1>회원가입 폼입니다.</h1>
		<form action="signup.jsp" method="post" id="signupForm" novalidate>
			<div>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id" />
				<small>영문자 소문자로 시작하고 5~10 글자 이내로 입려하세요</small>
				<div class="invalid-feedback">사용할수없는 아이디 입니다.</div>
			</div>
			
			<button type="submit">가입</button>
		</form>
	</div>
	<script>
		/*
			폼에 submit 이벤트가 발생하면 입력한 내용을 검증해서 조건을 만족하지 못하면
			폼 제출을 막는예제
		*/
		//아이디를 검증할 정규표현식
		const reg_id= /^[a-z].{4,9}$/;
		
		//폼안에 있는 submit 버튼을 누르면 form이 제출괴는 기본동작
		document.querySelector("#signupForm").addEventListener("submit", (event)=>{
		//document.querySelector("button[type=submit]").addEventListener("click", (event)=>{
			
			//입력한 id를 읽어온다.
			const inputId = document.querySelector("#id").value;
			
			//만일 아이디를 검증하는 정규 표현식을 통과하지않았다면
			if(!reg_id.test(inputId)){
				//폼제출막기
				event.preventDefault();
				//.invalid-feedback 인 div를 보이게 해보세요 (display:block)  //div block요소라서
				document.querySelector(".invalid-feedback").style.display="block";			
			}
		});		
	</script>
</body>
</html>