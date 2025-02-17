package com.example.spring04.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.spring04.dto.MemberDto;

@Controller
public class TestController {
	
	@GetMapping("/sequence")
	public String sequence() {
		
		return "test/sequence";
	}

	@GetMapping("/member/list")
	public String memberList(Model model) {
		
		//DB에서 select한 결과라고 가정하자
		MemberDto dto1 = MemberDto.builder().num(1).name("김구라").addr("노량진").build();
		MemberDto dto2 = MemberDto.builder().num(2).name("해골").addr("행신동").build();
		MemberDto dto3 = MemberDto.builder().num(3).name("원숭이").addr("상도동").build();
		
		// read only List
		List<MemberDto> list=List.of(dto1, dto2, dto3);
		
		//Model 객체에 "list"라는 키값으로 담기
		model.addAttribute("list", list);
		
		return "member/list";
	}
	
	@GetMapping("/member")
	public String member(Model model) {
		// lombok의 기능을 이용해서  MemberDto객체를 얻어내고
		MemberDto dto = MemberDto.builder()
				.num(1).name("구라핑").addr("노량진").build();
		//해당 데이터를 Model객체에 담고 
		//(모델에 담은건 자동으로 리퀘스트에 담김-> 리퀘스트에 담긴건 templates페이지에서 읽을수있다.)
		model.addAttribute("dto", dto);
		// view Page로 이동해서 응답하기
		return "member/info";
	}
	
	@GetMapping("/notice")
	public String notice(Model model) {
		List<String> noticeList=List.of("Thymeleaf view engine을 배워보아요로요로용", 
				"어쩌굿", "저쩌굿", "공지사항갯수를", "늘려보겠다 츄~", "사랑의 하츄핑~");
		model.addAttribute("noticeList", noticeList);
		return "post/notice";
	}
	

}
