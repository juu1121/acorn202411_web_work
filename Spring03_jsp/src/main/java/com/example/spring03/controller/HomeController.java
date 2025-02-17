package com.example.spring03.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.spring03.dto.MemberDto;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model) {
		//공지사항  //이 notice문자열을 담고싶으면 model을 매개변수에 선언!
		List<String> notice = List.of("Spring Boot 시작업니다.", "어쩌구", "저쩌구");
		
		//공지사항을 "notice"라는 키값으로 담기
		//3개의 문자열이 notice라는 키값으로 담김!
		model.addAttribute("notice", notice);
		
		
		//view page의 위치 (응답을 위임할 jsp페이지의 위치)를 리턴해준다.
		//return "/WEB-INF/views/home.jsp";
		// "/WEB-INF/views/" + home + ".jsp"
		return "home";
	}
	
	//컨트롤러 메소드안에서 HttpServletRequest, HttpServletResponse, HttpSession등의 객체가 필요하면 매개변수안에 선언하면된다.
	@GetMapping("/fortune")
	public String fortune(HttpServletRequest request) {
		//오늘의 운세를 얻어오는 비지니스 로직 수행
		String fortuneToday="동쪽으로 가면 귀인을 만나요!";
		
		//오늘의 운세를 request scope에 담고
		request.setAttribute("fortuneToday", fortuneToday);
		
		//view page로 forward 이동해서 응답하기
		//return "/WEB-INF/views/fortune.jsp";
		return "fortune";
	}
	
	@GetMapping("/fortune2")
	public String fortune2(Model model) {
		//오늘의 운세를 얻어오는 비지니스 로직 수행
		String fortuneToday="동쪽으로 가면 귀인을 만나요!";
		
		//Model 객체에 응답에 필요한 데이터를 담으면 자동으로 request영역에 담긴다.
		model.addAttribute("fortuneToday", fortuneToday);
		
		//view page로 forward 이동해서 응답하기
		return "fortune";
	}	
	
	@GetMapping("/member")
	public String member(Model model) {
		//MemberDto dto = MemberDto.builder().num(1).name("김구라").addr("노량진").build();  //dto객체를 한줄로 얻어내기
		//lombok을 활용해서 MemberDto객체를 만들고
		MemberDto dto = MemberDto.builder()
				.num(1)
				.name("김구라")
				.addr("노량진")
				.build();
		//Model 객체에 "dto"라는 키값으로 MemberDto객체를 담고
		model.addAttribute("dto", dto);
		
		// /WEB-INF/views/member/info.jsp 페이지로 forward 이동해서 응답하기
		return "member/info";
	}
}
