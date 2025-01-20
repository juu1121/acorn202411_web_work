<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/file/upload_form4.jsp</title>
<style>
	#image{
		display:none;
	}
	/*이미지 동그랗게만들기 */
	#profileImage{
		width:200px;
		height: 200px;
		border:1px solid #cecece;
		border-radius:50%;
		
	}
</style>
</head>
<body>
	<div class="container">
		<h3>이미지 단독 업로드 테스트</h3>
		<a href="javascript:" id="profileLink" >
			<svg xmlns="http://www.w3.org/2000/svg" width="200" height="200" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
				<path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
				<path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
			</svg>
		</a>
		<br />
	
		<!-- form으로 감싸지 않았기때문에 전송하는 방식이 다름 -->
		<!-- accept="image/*" =>이미지만 선택할수있음(목록에 아예 안 보이넹) -->
		<input type="file" id="image" accept="image/*" />
	</div>
	<script>
		//링크를 클릭했을때
		document.querySelector("#profileLink").addEventListener("click", ()=>{
			//input type="file"요소를 강제 클릭해서 파일 선택창을 띄운다.
			//.click() -> 해당요소를 클릭!
			document.querySelector("#image").click();
		});
		
		
		//새로운 이미지를 선택했을때 input요소에는 change이벤트가 발생한다.
		document.querySelector("#image").addEventListener("change", (event)=>{
			//여기서 event.target은 input type="file" 요소이다. //change이벤트가 일어난 타겟!
			//선택된 파일 데이터
			//이미지를 여러개선택하게할수도있지만, 지금은 하나여서 [0]
			const fileData = event.target.files[0];
			//FormData객체에 myImage라는 키값으로 파일 데이터 담기
			const data = new FormData();
			data.append("myImage",fileData);
			//fetch()함수를 이용해서 업로드하고 응답받은 데이터를 이용해서 이미지 출력하기
			fetch("${pageContext.request.contextPath}/file/upload4", {
				method: "post",
				body:data
			})
			.then(res=>res.json())
			.then(data=>{
				//data.saveFileName은 업로드된 이미지의 저장된 파일명이다.
				console.log(data);
				//img요소를 만들 문자열 구성하기  <%--백틱안에서 문자열이어붙일때 ${}쓰기--%>
				const img=`
					<img id="profileImage" src="${pageContext.request.contextPath}/upload/\${data.saveFileName}"/>
				`;
				document.querySelector("#profileLink").innerHTML=img;
			});
		});
	</script>
</body>
</html>