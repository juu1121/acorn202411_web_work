<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/fetch/test02.jsp</title>
</head>
<body>
	<input type="text" id="msg" placeholder="메세지 입력..."/>
	<button id="sendBtn">전송</button>
	<button id="sendBtn2">전송2</button>
	<!-- jquery 로딩 -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
	<script>
		// id 가 sendBtn2 인 곳에 "click" 이벤트가 발생하면 호출될 함수 등록 
		$("#sendBtn2").on("click", ()=>{
			// id 가 msg 인곳의 value 값을 읽어와서 msg 라는 상수에 담기
			const msg = $("#msg").val();
			fetch("send2.jsp?msg="+msg)
			.then(res=>res.json())
			.then(data=>{
				console.log(data);
			})
			.catch(error=>{  //에러가 났을때 직접처리할수다 
				//(여기서는 json형식잘못만들면 파싱과정에서 에러가나겠지?)
				console.log("에러정보"+error);
			})
		});
	
	
		document.querySelector("#sendBtn").addEventListener("click", ()=>{
			//입력한 문자열 읽어오기
			const msg=document.querySelector("#msg").value;
			//fetch 함수를 호출하면서 GET 방식 파라미터로 send.jsp 페이지를 요청하면서 전달한다.
			fetch("send.jsp?msg="+msg)
			.then(res=>res.text())
			.then(data=>{
				console.log(data);
			});
		});
	</script>
</body>
</html>