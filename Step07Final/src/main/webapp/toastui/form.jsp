<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Step11_ToastEditor.html</title>
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
        <h1>Toast UI Editor 사용하기</h1>
        <div id="editor"></div>
        <form action="save.jsp" method="post" id="saveForm">
        	<input type="hidden" name="content" id="content" />
        	<button type="submit">저장</button>
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
        
        /*
        	에디터에 입력한 내용 읽어오는 방법
        	
        	1. markdown //마크다운작성한거
        	editor.getMarkdown();
        
        	2. html //마크다운안쓰면 이걸쓰겠징
        	editor.getHTML();
        */
		document.querySelector("#saveForm").addEventListener("submit", (event)=>{
			//에디터에 입력한 내용을 input type="hidden"의 value값으로 넣어준다.
			document.querySelector("#content").value=editor.getHTML();
			//폼에 이거말고 다른 내용도 입력했다면 검증을 여기서 하고
			
			//폼전송을 막고싶다면
			//event.preventDefault();가 실행되게하면된다.
		});
    </script>
</body>
</html>