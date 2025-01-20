<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/fetch/test04.jsp</title>
</head>
<body>
	<button id="getBtn">친구이름 목록 받아오기_jquery</button>
	<button id="getBtn2">친구이름 목록 받아오기_바닐라</button>
	<ul id="friendList">
	
	</ul>
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
	<script>
		$("#getBtn").on("click", ()=>{
				fetch("friends.jsp")
				.then(res=>res.json())
				.then(data=>{
					console.log(data);
					for(let i=0; i<data.length; i++){
						//li요소를 만들어서 선택하고 innerText에 출력한후 jquery객체(배열)을 상수에 담기
						const li = $("<li>").text(data[i]);
						//id가 friendList인 요소에 추가하기
						$("#friendList").append(li);
		            }
					
				})
				.catch(error=>{ 
					console.log("에러정보"+error);
				})
			});
		
		document.querySelector("#getBtn2").addEventListener("click", ()=>{
		
			fetch("friends.jsp")
			.then(res=>res.json())
			.then(data=>{
				
				for(let i=0; i<3; i++){
					const li=document.createElement("li");
					li.innerText=data[i];
					document.querySelector("#friendList").append(li);
	            }
				
			});
		});
	
	 
	</script>
</body>
</html>