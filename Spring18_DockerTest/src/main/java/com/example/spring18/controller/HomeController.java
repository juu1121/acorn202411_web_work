package com.example.spring18.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		
		// templates/home.html view페이지에서 응답
		return "home";
	}

}
