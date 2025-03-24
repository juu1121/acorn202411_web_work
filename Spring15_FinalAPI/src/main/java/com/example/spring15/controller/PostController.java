package com.example.spring15.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.spring15.dto.CommentDto;
import com.example.spring15.dto.CommentListRequest;
import com.example.spring15.dto.PostDto;
import com.example.spring15.dto.PostListDto;
import com.example.spring15.service.PostService;

import jakarta.servlet.http.HttpSession;

@RestController
public class PostController {
	@Autowired private PostService service;
	
	@PatchMapping("/posts/{num}/comments")
	public Map<String, Boolean> updateComment( @RequestBody CommentDto dto){ 
												//dto에는 댓글의 글번호와 댓글의 내용이 들어있다. 
		service.updateComment(dto);
		return Map.of("isSuccess", true);
		//컨트롤러에서 @ResponseBody의 의미는 여기서 리턴해주는 데이터를 직접 응답의 body에 넣는다(직접 클라이언트한테 준다)
		//ex) 저기서 리턴한게 문자 : 문자열을 준다 // Map(특정키값으로 데이터를 가지고있는것)/list/Dto일경우(dto의 필드를 이용) json으로 변경해서 준다
	}
	
	@GetMapping("/post/delete-comment")
	@ResponseBody 
	public Map<String, Boolean> DeleteComment(long num){
		
		service.deleteComment(num);
		// map을 리턴하면서 @ResponseBody 하면, 리턴문자열을 응답하는거
		// @ResponseBody 어노테이션을 붙여 놓고 아래의 데이터를 리턴하면  {"isSuccess":true} 형식의 json문자열이 응답한다. 
		return Map.of("isSuccess", true);
	}
	
	@GetMapping("/posts/{num}/comments")
	public Map<String, Object> commentList(@PathVariable("num") long num, int pageNum){ 
																//파라미터 pageNum선언만해서 추출
		//CommentListRequest에 필요한 정보를 담고
		CommentListRequest clr = new CommentListRequest();
		clr.setPostNum(num);
		clr.setPageNum(pageNum);
		//서비스를 이용해서 댓글 목록 정보를 얻어내서 응답한다.
		return service.getComments(clr); //여기서 Map이 리턴되고, 
		//이 Map의 형태는 {"list" : [{댓글목록},{},[}], "totalPageCount" : 19(페이지의갯수)} 
		
	}
	
	//댓글 저장 요청처리
	@PostMapping("/posts/{num}/comments")
	public CommentDto saveComment(@PathVariable(value="num") long num, @RequestBody CommentDto dto) { //이 dto에는 content/postNum/targetWriter이 담겨있다.
		//댓글이 원글의댓글인지, 대댓글인지 확인하는 법은 parentNum이 0이냐 아니냐에따른다. (parentNum은 있을수도없을수도=전달되는게없으면 디폴트0이있겠지)
		
		//dto에 원글의 글 번호담기
		dto.setPostNum(num);
		//서비스를 이용해서 댓글 저장
		service.createComment(dto); //댓글저장 , 여기서 parentNum확인하고 추가...
		
		return dto;
	}
	
	//글 삭제 요청 처리
	@DeleteMapping("/posts/{num}")
	public PostDto delete(@PathVariable(value="num") long num) {
		//삭제하기전 글 정보를 읽고
		PostDto dto = service.getByNum(num);
		//서비스 객체를 이용해 삭제하기
		service.deletePost(num);
		//삭제된 글 정보를 리턴하가.
		return dto;
	}
	
	//글 수정 반영요청처리
	@PatchMapping("/posts/{num}")
	public PostDto update(@PathVariable(value="num") long num, @RequestBody PostDto dto) {
		//num에는 글번호 + dto에는 title과 content가 담겼다
		//글번호를 dto에 담는다.
		dto.setNum(num);
		//서비스를 이용해서 수정반영
		service.updatePost(dto);

		//수정된 글 정보 리턴
		return dto;
	}
	
//	//글 수정 폼 요청처리
//	@GetMapping("/post/edit")
//	public String edit(long num, Model model) {
//		//수정할 글정보를 얻어와서 Model객체에 담는다.
//		PostDto dto = service.getByNum(num);
//		model.addAttribute("dto", dto);
//		return "post/edit";
//	}
//	
//	@GetMapping("/post/new")
//	public String newForm() {
//		return "post/new";
//	}
	
	@PostMapping("/posts")
	public Map<String, Object> save(@RequestBody PostDto dto) {
		//새글을 저장하고 글번호를 리턴받는다.
		long num = service.createPost(dto);
		
		return Map.of("num", num);
	}
	/*
	 * dto에 글번호만 있는 경우도 있고, 검색과 관련된 정보가 같이 있을수도있다.
	 * dto에 num or num/condition/keyword가 있을수도있다.
	 */
	@GetMapping("/posts/{num}")
	public PostDto view(@PathVariable(name="num") long num, PostDto dto) {
		//글 번호는 경로변수에, 검색키워드가 있다면 해당 정보는 PostDto객체에 담겨 있다.
		
		//글번호를 dto에 담는다.
		dto.setNum(num);
		//글 자세한 정보를 얻어와서
		PostDto resultDto = service.getDetail(dto);
		//응답한다.
		return resultDto;
	}
	
	/*
	 *	pageNum이 파라미터로 넘어오지 않으면 pageNum의 default 값을 1로 설정
	 *	검색 키워드도 파라미터로 넘어오면 PostDto의 condition과 keyword가 null이 아니다.
	 *	검색키워드가 넘어오지않으면 PostDto의 condition 과 keyword는 null이다.  
	 */
	@GetMapping("/posts")
	public PostListDto list(@RequestParam(defaultValue = "1") int pageNum, PostDto search) {
		//글 목록과 검색 키워드 관련 정보가 들어있는 PostListDto
		PostListDto dto = service.getPosts(pageNum, search);
		//JSON 문자열이 응답되도록 dto를 리턴한다.
		return dto;
	}
}
