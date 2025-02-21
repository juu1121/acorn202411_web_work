package com.example.spring10.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model) {
		//공지사항
		List<String> noticeList=List.of("Spring Boot 프로젝트 시작입니다.",
				"열심히 해 보아요", "어쩌구...", "저쩌구...");
		
		//응답에 필요한 데이터를 Model객체에 담는다.
		model.addAttribute("noticeList", noticeList);
		//view page에서 응답하기
		return "home";
	}
	
}
