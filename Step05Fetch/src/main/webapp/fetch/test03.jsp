<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/fetch/test03.jsp</title>
</head>
<body>
	<button id="getBtn">회원정보 가져오기 jquery</button>
	<button id="getBtn2">회원정보 가져오기 바닐라코드</button>
	<p>번호 : <strong id="num"></strong></p>
	<p>이름 : <strong id="name"></strong></p>
	<p>주소 : <strong id="addr"></strong></p>
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
	<script>
	
	$("#getBtn").on("click", ()=>{
		
		fetch("member.jsp")
		.then(res=>res.json())
		.then(data=>{
			//jquery의 .text()동작을 이용해서 data object에 담긴 정보를 innerText에 출력하기
			console.log(data);
			$("#num").text(data.num);
			$("#name").text(data.name);
			$("#addr").text(data.addr);
		})
		.catch(error=>{
			console.log("에러정보"+error);
		});
	});
	
	
	document.querySelector("#getBtn2").addEventListener("click", ()=>{
		
		fetch("member.jsp")
		.then(res=>res.json())
		.then(data=>{
			document.querySelector("#num").innerText=data.num;
			document.querySelector("#name").innerText=data.name;
			document.querySelector("#addr").innerText=data.addr;
			
		});
	});
	
	
	
	</script>
</body>
</html>