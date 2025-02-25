package com.example.spring10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.spring10.dto.PostDto;
import com.example.spring10.dto.PostListDto;
import com.example.spring10.service.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {
	@Autowired private PostService service;
	
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
