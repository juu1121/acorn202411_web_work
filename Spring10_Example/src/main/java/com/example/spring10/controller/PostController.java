package com.example.spring10.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.spring10.dto.CommentDto;
import com.example.spring10.dto.CommentListRequest;
import com.example.spring10.dto.PostDto;
import com.example.spring10.dto.PostListDto;
import com.example.spring10.service.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {
	@Autowired private PostService service;
	
	@PostMapping("/post/update-comment")
	@ResponseBody
	public Map<String, Boolean> updateComment(CommentDto dto){
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
	
	@GetMapping("/post/comment-list")
	@ResponseBody  //페치요청이오니까, json으로 응답 //dto에 저장된 내용을 json으로 응답하기 위한 어노테이션
	Map<String, Object> commentList(CommentListRequest clr){
		//CommentListRequest는 dto임
		//CommentListRequest 객체에는 댓글의 pageNum과 원글의 글번호 postNum이 들어있다.
		//clr을 전달받아서 Map을 리턴하는 구조로 만들거임!
		
		return service.getComments(clr); //여기서 Map이 리턴되고, 응답의 body에 넣음
	}
	
	//댓글 저장 요청처리
	@PostMapping("/post/save-comment")
	@ResponseBody //페치요청이오니까, json으로 응답 //dto에 저장된 내용을 json으로 응답하기 위한 어노테이션
	public CommentDto saveComment(CommentDto dto) { //이 dto에는 content/postNum/targetWriter이 담겨있다.
		//댓글이 원글의댓글인지, 대댓글인지 확인하는 법은 parentNum이 0이냐 아니냐에따른다. (parentNum은 있을수도없을수도=전달되는게없으면 디폴트0이있겠지)
		service.createComment(dto); //댓글저장 , 여기서 parentNum확인하고 추가...
		
		
		return dto;
	}
	
	//글 삭제 요청 처리
	@GetMapping("/post/delete")
	public String delete(long num) {
		service.deletePost(num);
		return "post/delete";
	}
	
	//글 수정 반영요청처리
	@PostMapping("/post/update")
	public String update(PostDto dto, RedirectAttributes ra) {
		service.updatePost(dto);
		/*
		 * RedirectAttributes 객체에 FlashAttribute 로 담은 내용은
		 * redirect 이동된 요청을 처리하는 곳의 Model 객체에 자동으로 담긴다.
		 */
		ra.addFlashAttribute("updateMessage", dto.getNum()+"번 글을 수정했습니다.");
		//수정 반영후 글 자세히 보기로 이동
		return "redirect:/post/view?num="+dto.getNum();
	}
	
	//글 수정 폼 요청처리
	@GetMapping("/post/edit")
	public String edit(long num, Model model) {
		//수정할 글정보를 얻어와서 Model객체에 담는다.
		PostDto dto = service.getByNum(num);
		model.addAttribute("dto", dto);
		return "post/edit";
	}
	
	@GetMapping("/post/new")
	public String newForm() {
		return "post/new";
	}
	
	@PostMapping("/post/save")
	public String save(PostDto dto, RedirectAttributes ra) {
		//새글을 저장하고 글번호를 리턴받는다.
		long num = service.createPost(dto);
		ra.addFlashAttribute("saveMessage", "글 저장성공!");
		//글 자세히 보기로 리다일렉트 이동하라는 응답하기
		return "redirect:/post/view?num="+num;
	}
	/*
	 * dto에 글번호만 있는 경우도 있고, 검색과 관련된 정보가 같이 있을수도있다.
	 * dto에 num or num/condition/keyword가 있을수도있다.
	 */
	@GetMapping("/post/view")
	public String view(PostDto dto, Model model, HttpSession session) { 
		
		//dto를 이용해 글 자세한값을 가져와서 담음!
		PostDto resultDto = service.getDetail(dto);
		model.addAttribute("postDto", resultDto);
		
		//새로 작성한 글이 아닌 경우에만 조회수 관련 처리를 한다.
		if(model.getAttribute("saveMessage")==null) {
			//조회수 관련 처리를 한다.
			String sessionId=session.getId();
			service.manageViewCount(dto.getNum(), sessionId);
		}
		
		return "post/view";
	}
	
	/*
	 *	pageNum이 파라미터로 넘어오지 않으면 pageNum의 default 값을 1로 설정
	 *	검색 키워드도 파라미터로 넘어오면 PostDto의 condition과 keyword가 null이 아니다.
	 *	검색키워드가 넘어오지않으면 PostDto의 condition 과 keyword는 null이다.  
	 */
	@GetMapping("/post/list")
	public String list(@RequestParam(defaultValue = "1") int pageNum, PostDto search, Model model) {
		PostListDto dto = service.getPosts(pageNum, search);
		model.addAttribute("dto", dto);
		//테스트를 위한 임시데이터
		//model.addAttribute("num", 1);
		//model.addAttribute("name", "김구라");
		return "post/list";
	}
}
