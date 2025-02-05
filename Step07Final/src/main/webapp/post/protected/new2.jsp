<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/post/form/new2.jsp_에디터변경하기</title>
<style>
	#content{
		width: 100%;
		height: 300px;
	}
</style>
</head>
<body>
	<div class="container">
		<h1>새글 작성 양식</h1>
		<form action="insert.jsp" method="post">
			<div>
				<label for="title">제목</label>
				<input type="text" name="title" id="title"/>
			</div>
			<div>
				<label for="content">내용</label>
				<textarea name="content" id="content"></textarea>
				<%--textarea 스마트에디터의 크기 --%>
			</div>
			<button type="submit" onclick="submitContents(this)">저장</button>
		</form>
	</div>
	<%--
		[ SmartEditor 를 사용하기 위한 설정 ]
		
		1. webapp 에 SmartEditor  폴더를 복사해서 붙여 넣기
		2. webapp 에 upload 폴더 만들어 두기
		3. <textarea id="content" name="content"> 
		   content 가 아래의 javascript 에서 사용 되기때문에 다른 이름으로 바꾸고 
		      싶으면 javascript 에서  content 를 찾아서 모두 다른 이름으로 바꿔주면 된다. 
		3. textarea 의 크기가 SmartEditor  의 크기가 된다.
		5. 폼을 제출하고 싶으면  submitContents(this) 라는 javascript 가 
		      폼 안에 있는 버튼에서 실행되면 된다.
 	--%>
	
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