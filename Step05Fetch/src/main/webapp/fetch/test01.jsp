<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test01.jsp</title>
</head>
<body>
	<h3>fetch 함수 테스트</h3>
	<button id="myBtn">눌러보셈</button>
	<script>
		document.querySelector("#myBtn").addEventListener("click", ()=>{
			//javascript로 서버에 요청하기
			/*
			서버(jsp or servlet)에서 응답한 문자열이 json형식이면 return res.json();
			그 이외의 형식이면 html,xml,plain text등등 return res.text();
			*/
			
			
			//요청은 자바스크립트로하되, 함수로 받는거야..
			//then함수안에는 함수를 넣어줘야해여->함수호출하면서 함수넣어줌
			//첫번째then에 전달된함수가호출 -> 두번째then에 전달된 함수가 호출 :이런순사
			fetch("${pageContext.request.contextPath }/index.jsp") //요청할주소전달  
			.then(function(res){
				return res.text();
			})
			.then(function(data){
				console.log(data);
			});
			
			fetch("${pageContext.request.contextPath }/index.jsp")
			.then(res=>{
				return res.text();
			})
			.then(data=>{
				console.log(data);
			});
			
			//최종형태!
			//페이지 전환없이 자바스크립트로 요청을 하면, 응답된문자열이 두번째then()에 전달한 함수의 매개변수에 전달된다.
			fetch("${pageContext.request.contextPath }/index.jsp")
			.then(res=>res.text()) //매개변수=>리턴할값
			.then(data=>{
				/*
				위의 then()함수에서 res.json()를 리턴하면 data는 응답된 json문자열을 JSON.parse() 과정을 이미 거친 object나 array이다.
				위의 then()함수에서 res.text()를 리턴하면 data는 서버가 응답한 문자열(string)이다.
				*/
				console.log(data);
			});
			console.log("aaaaaaaaaaaaa");
		});
	</script>
</body>
</html>