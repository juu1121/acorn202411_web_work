<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//Editor로 입력한 html 형식의 문자열을 DB에 저장해다가 필요시 불러와서 그대로 html 형식으로 출력하면 된다.
	String content = request.getParameter("content");
	System.out.println(content);
	//javascript 문자열의 구조를 망가뜨리지 않게 하기 위해
	String content2= content.replace("\"","\\\""); // 글 내용중에 "(따옴표)가 있다면 \"(역슬래시따옴표)로 변경
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/toastui/save.jsp</title>
    <!-- toast editor 를 사용하기위한 css, jacascript로딩-->
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
    <style>
        .container{ 
            width : 80%;
            margin: 0 auto; 
            /*가운데정렬하려고, 폭설정+마진자동*/
        }
    </style>
</head>
<body>
	<div class="container">
		<h1>게시글</h1>
		<div><%=content %></div>
		
		<h1>글 수정 폼</h1>
		<div id="editor"></div>
		<form action="save.jsp" method="post" id="updateForm">
			<input type="hidden" name="content" id="content" />
			<button type="submit">수정확인</button>
		</form>
	</div>
	<script>
	    //Editor 클래스 얻어내기
	    const Editor = toastui.Editor;
	    //Editor 객체 생성하면서 option 전달하기
	    const editor = new Editor({
	        el: document.querySelector('#editor'), //어디에 Editor를 만들것인지
	        height: '500px', //높이
	        initialEditType: 'wysiwyg', //Editor type설정 markdown | wysiwyg
	        previewStyle: 'vertical' //미리보기설정 vertical | tab
	    });
	    
	    editor.setHTML("<%= content2%>")
	    document.querySelector("#updateForm").addEventListener("submit", (event)=>{
			//에디터에 입력한 내용을 input type="hidden"의 value값으로 넣어준다.
			document.querySelector("#content").value=editor.getHTML();
			//폼에 이거말고 다른 내용도 입력했다면 검증을 여기서 하고
			
			//폼전송을 막고싶다면
			//event.preventDefault();가 실행되게하면된다.
		});
    
	</script>
</body>
</html>