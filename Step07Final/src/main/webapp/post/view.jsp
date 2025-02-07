<%@page import="test.user.dto.SessionDto"%>
<%@page import="test.post.dao.PostDao"%>
<%@page import="test.post.dto.PostDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//검색조건이 있는지 읽어와 본다.
	String condition=request.getParameter("condition");
	String keyword=request.getParameter("keyword");
	String findQuery=null;
	//있다면 dto 에 해당 정보를 담는다.
	PostDto findDto=new PostDto();
	if(condition != null){
		//findDto=new PostDto();
		findDto.setCondition(condition);
		findDto.setKeyword(keyword);
		findQuery="&condition="+condition+"&keyword="+keyword;
	}
	//자세히 보여줄 글의 번호를 읽어온다. 
	int num=Integer.parseInt(request.getParameter("num"));
	findDto.setNum(num);
	
	//DB 에서 해당 글의 정보를 얻어와서 
	PostDto dto=PostDao.getInstance().getData(findDto);
	
	//세션 아이디를 읽어와서 
	String sessionId=session.getId();
	//이미 읽었는지 여부를 얻어낸다 
	boolean isReaded=PostDao.getInstance().isReaded(num, sessionId);
	if(!isReaded){
		//글 조회수도 1 증가 시킨다
		PostDao.getInstance().addViewCount(num);
		//이미 읽었다고 표시한다. 
		PostDao.getInstance().insertReaded(num, sessionId);
	}
	
	request.setAttribute("dto", dto);
	request.setAttribute("findDto", findDto);
	request.setAttribute("findQuery", findQuery);
	//응답한다 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/post/view.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<style>
    #contents {
        margin-top: 20px;
        padding: 20px;
        background-color: #fefefe;
        border-radius: 10px;
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
        border: 1px solid #ddd;
        transition: transform 0.3s ease, box-shadow 0.3s ease;
    }
    #contents:hover {
        transform: translateY(-5px);
        box-shadow: 0 15px 30px rgba(0, 0, 0, 0.3);
    }
    
    #content{
    	width: 100%;
    	height: 300px;
    }
	/* 댓글 프로필 이미지를 작은 원형으로 만든다. */
	.profile-image{
		width: 50px;
		height: 50px;
		border: 1px solid #cecece;
		border-radius: 50%;
	}
	/* ul 요소의 기본 스타일 제거 */
	.comments ul{
		padding: 0;
		margin: 0;
		list-style-type: none;
	}
	
	/* .reply_icon 을 li 요소를 기준으로 배치 하기 */
	.comments li{
		position: relative;
	}
	.comments .reply-icon{
		position: absolute;
		top: 1rem;
		left: 1rem;
		color: red;
	}
	
	/* 대댓글을 들여 쓰기 위한 클래스 */
	.indent{
		padding-left: 50px;
	}
	
	/* 답글 아이콘은 일단 보이지 않게  */
	.reply-icon{
		display: none;
	}
	
	.comment-form, .re-insert-form, .update-form{
		display: flex;
	}
	
	.comment-form textarea, .re-insert-form textarea, .update-form textarea{
		height: 100px;
		flex-grow: 1;
	}
	
	.comment-form button, .re-insert-form button, .update-form button{
		flex-basis: 100px;
	}
	/* 대댓글폼은 일단 숨겨 놓는다 */
	.re-insert-form, .update-form{
		display: none;
	}
	
	/* 댓글 출력 디자인 */
	.comments pre {
	  display: block;
	  padding: 9.5px;
	  margin: 5px 0;
	  font-size: 13px;
	  line-height: 1.42857143;
	  color: #333333;
	  word-break: break-all;
	  word-wrap: break-word;
	  background-color: #f5f5f5;
	  border: 1px solid #ccc;
	  border-radius: 4px;
	}
	.loader{
		/* 로딩 이미지를 가운데 정렬하기 위해 */
		text-align: center;
		/* 일단 숨겨 놓기 */
		display: none;
	}
	/* 회전하는 키프레임 정의 */
	@keyframes rotateAni{
		from{
			transform: rotate(0deg);
		}
		to{
			transform: rotate(360deg);
		}
	}
	/* 회전하는 키프레임을 로더 이미지에 무한 반복 시키기 */
	.loader svg{
		animation: rotateAni 1s ease-out infinite;
	}
	
	body{
		padding-bottom: 200px;
	}
</style>
</head>
<body>
	<div class="container">
		<c:if test="${dto.prevNum ne 0}">
			<a href="view.jsp?num=${dto.prevNum}${findQuery}">이전글</a>
		</c:if>
		<c:if test="${dto.nextNum ne 0}">
			<a href="view.jsp?num=${dto.nextNum}${findQuery}">다음글</a>
		</c:if>
		<c:if test="${not empty findDto.condition}">
			<p>
				<strong>${findDto.condition }</strong> 조건
				<strong>${findDto.keyword }</strong>검색어로 검색된 내용 자세히보기
			</p>
		</c:if>
		<h3>글 상세 보기</h3>
		<table class="table table-bordered">
			<tr>
				<th>글번호</th>
				<td>${dto.num }</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${dto.writer }</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${dto.title }</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${dto.viewCount }</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${dto.createdAt }</td>
			</tr>
			<tr>
				<th>수정일</th>
				<td>${dto.updatedAt }</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="contents">${dto.content }</div>
				</td>
			</tr>
		</table>
		
		<%-- 만일 글 작성자가 로그인된 아이디와 같다면 수정, 삭제 링크를 제공한다. --%>
		<c:if test="${dto.writer eq sessionDto.userName }">
			<a class="btn btn-outline-success btn-sm" href="${pageContext.request.contextPath }/post/protected/edit.jsp?num=${dto.num }">수정</a>
			<a class="btn btn-outline-danger btn-sm" href="javascript:" onclick="deleteConfirm()">삭제</a>
			<script>
				function deleteConfirm(){
					const isDelete=confirm("이 글을 삭제 하겠습니까?");
					if(isDelete){
						//javascript 를 이용해서 페이지 이동 시키기
						location.href="${pageContext.request.contextPath }/post/protected/delete.jsp?num=${dto.num}";
					}
				}
			</script>
		</c:if>
		<h4>댓글을 입력해 주세요</h4>
		<!-- 원글에 댓글을 작성할 폼 -->	
		<form class="comment-form" action="protected/comment-insert.jsp" method="post">
			<!-- 원글의 글번호+원글의 작성자를 보낼준비 -->
			<!-- 원글의 글번호가 댓글의 ref_group 번호가 된다. -->
			<input type="hidden" name="postNum" value="${dto.num}"/>
			<!-- 원글의 작성자가 댓글의 대상자가 된다. -->
			<input type="hidden" name="targetWriter" value="${dto.writer}"/>
			<!-- sessionDto가 비어있으면 로그인 안 한상태-->
		 	<textarea name="content">${empty sessionDto ? '댓글 작성을 위해 로그인이 필요합니다' : ''}</textarea>
			<button type="submit">등록</button>
		</form>
		<!-- 댓글 목록 -->
		<div class="comments">
			<ul>
			
			</ul>
		</div>
	</div>
	<script>
		//로그인된 사용자의 이름
		const userName="${sessionDto.userName}";
	
		document.querySelector(".comment-form").addEventListener("submit", (e)=>{
			//폼제출막기
			e.preventDefault();
			//폼에 작성된 내용을 이용해서 query 문자열을 얻어낸다
			const formData=new FormData(e.target);
			const queryString = new URLSearchParams(formData).toString();
			fetch() 함수를 이용해서 댓글 정보를 페이지전환없이 서버에 전송한다.
			fetch("protected/comment-insert.jsp",{
				method:"POST",
				headers: { "Content-Type": "application/x-www-form-urlencoded" },
				body:queryString  //postNum=1&targetWriter=xxx  //폼에입력한내용을 이용해서 문자열얻어냄 
			})
			.then(res=>res.json())
			.then(comment=>{
				//저장된 댓글 정보가 응답된다.
				console.log(comment);
				//li요소를 만들어서 
				const li = document.createElement("li");
				li.classList.add(comment.num !== comment.parentNum ? "indent" : "not");
				// 프로필 이미지 처리
                const profileImage = comment.profileImage 
                    ? `<img class="profile-image" src="${pageContext.request.contextPath}/upload/\${comment.profileImage}" alt="Profile Image">`
                    : `<svg class="profile-image" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                        <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                    </svg>`;
                // li요소안에 dl을 출력한다.
				li.innerHTML=`
					<dl>
						<dt>
							\${profileImage}
							<!-- 댓글 작성자 -->
							<span>\${comment.writer}</span>
							<!-- 댓글 대상자를 조건부로 출력 (대댓글에만 출력) -->
							\${comment.num != comment.parentNum ? '@'+comment.targetWriter : ''}
							<!-- 댓글 작성일자 -->
							<small>\${comment.createdAt}</small>
							<!-- 답글 링크 -->
							<a data-num="\${comment.num}" class="reply-link" href="javascript:">답글</a>
							<!-- 로그인된 유저가 쓴 댓글일 경우 수정, 삭제 링크를 제공한다 -->
							
						</dt>
						<dd>
							<pre id="content\${comment.num}">\${comment.content}</pre>
						</dd>
					</dl>
				`;
				document.querySelector(".comments ul").append(li);
			});
		});
	</script>
</body>
</html>








