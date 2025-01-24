<%@page import="test.post.dto.PostDto"%>
<%@page import="test.post.dao.PostDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. GET 방식 파라미터로 전달되는 수정할 파일의 글번호 읽어오기
	int num=Integer.parseInt(request.getParameter("num"));
	//2. DB 에서 해당글의 정보 얻어오기
	PostDto dto=PostDao.getInstance().getData(num);
	
	request.setAttribute("dto", dto);
	//3. 수정폼을 응답하기 
%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/post/protected/edit.jsp</title>
<style>
	#content{
		width: 100%;
		height: 300px;
	}
</style>
</head>
<body>
	<div class="container">
		<h1>글 수정 양식</h1>
		<form action="update.jsp" method="post">
			<div>
				<label  for="num">번호</label>
				<input type="text" id="num" name="num" value="${dto.num }" readonly/>
			</div>
			<div>
				<label for="title">제목</label>
				<input type="text" id="title" name="title" value="${dto.title }"/>
			</div>
			<div>
				<label for="content">내용</label>
				<textarea name="content" id="content" rows="10">${dto.content }</textarea>
			</div>
			<button onclick="submitContents(this)" class="btn btn-primary" type="submit">수정확인</button>
			<button class="btn btn-warning" type="reset">Reset</button>
		</form>
	</div>
	<!-- SmartEditor 에서 필요한 javascript 로딩  -->
	<script src="${pageContext.request.contextPath }/SmartEditor/js/HuskyEZCreator.js"></script>
	<script>
		let oEditors = [];
		
		//추가 글꼴 목록
		//let aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];
		
		nhn.husky.EZCreator.createInIFrame({
			oAppRef: oEditors,
			elPlaceHolder: "content",
			sSkinURI: "${pageContext.request.contextPath}/SmartEditor/SmartEditor2Skin.html",	
			htParams : {
				bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
				bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
				bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
				//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
				fOnBeforeUnload : function(){
					//alert("완료!");
				}
			}, //boolean
			fOnAppLoad : function(){
				//예제 코드
				//oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
			},
			fCreator: "createSEditor2"
		});
		
		function pasteHTML() {
			let sHTML = "<span style='color:#FF0000;'>이미지도 같은 방식으로 삽입합니다.<\/span>";
			oEditors.getById["content"].exec("PASTE_HTML", [sHTML]);
		}
		
		function showHTML() {
			let sHTML = oEditors.getById["content"].getIR();
			alert(sHTML);
		}
		//이 함수에는 submit 버튼의 참조값이 매개 변수에 전달된다.	
		function submitContents(elClickedObj) {
			
			//SmartEditor 에 의해 만들어진(작성한글) 내용이 textarea 의 value 가 되도록 한다. 
			oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);	// 에디터의 내용이 textarea에 적용됩니다.
			
			// 에디터의 내용에 대한 값 검증은 이곳에서 document.getElementById("content").value를 이용해서 처리하면 됩니다.
			
			try {
				// submit 버튼의 제출 대상이 되는 form 의 참조값을 얻어와서 submit() 호출해서 폼 제출 
				elClickedObj.form.submit();
			} catch(e) {}
		}
		
		function setDefaultFont() {
			let sDefaultFont = '궁서';
			let nFontSize = 24;
			oEditors.getById["content"].setDefaultFont(sDefaultFont, nFontSize);
		}
	</script>	
</body>
</html>