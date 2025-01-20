<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/file/upload_form3.jsp</title>
</head>
<body>
	<div class="container">
		<h3>이미지 업로드 폼</h3>
		<form action="${pageContext.request.contextPath}/file/upload3" method="post" 
			enctype="multipart/form-data" id="myForm">
			<input type="text" name="title" placeholder="설명 입력"/><br />
			이미지 <input type="file" name="myImage" accept="image/*"/><br />
			<button type="submit">업로드</button>
		</form>
		<img id="image" width="300"/>
	</div>
	<script>
		//발생한 이벤트에 대한 정보를 담고있는 object가 매개변수에 전달된다.
		document.querySelector("#myForm").addEventListener("submit", (event)=>{
			//기본동작(폼제출)막기
			event.preventDefault();
			//event.target => 해당 이벤트가 발생한 바로 그 요소의 참조값 (form의 참조값)
			//evetn.target == document.querySelector("#myForm")
			const data = new FormData(event.target);
			//오브젝트작성  {key:value, key:value...}
			//fetch함수를 이용해서 FormData전송하기 
			fetch("${pageContext.request.contextPath}/file/upload3", {
				method:"post",
				body:data
			})
			.then(res=>res.json())
			.then(data=>{
				console.log(data);
				//data.saveFileName은 upload폴더에 저장된 파일명이다.
				const requestPath = "${pageContext.request.contextPath}/upload/"+data.saveFileName;
				document.querySelector("#image").setAttribute("src", requestPath);
				//.setAttribute("속성명", 속성값);

			});
		});
	
	</script>
</body>
</html>