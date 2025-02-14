package com.example.spring02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * 클라이언트의 요청을 처리할 컨트롤러를 정의하고 bean으로 만들기
 * 이건 특별한기능을 수행하는 bean => so, Controller이라는 어노테이션붙임
 * */
@Controller
public class HelloController {
	
	@ResponseBody //리턴한걸출력
	@GetMapping("/fortune") //어떤방식, 어떤요청	
	public String fortune() {
		return "90일 남았츄";
	}
	
	@ResponseBody //리턴한걸출력
	@GetMapping("/hello") //클라이언트가 GET방식. "/hello"경로로 요청을 하면 이 메소드가 실행된다. //어떤방식, 어떤요청
	public String hello() {
		
		// String type을 리턴하면서 메소드에 @ResponseBody 어노테이션을 붙여놓으면
		// 여기서 리턴한 문자열이 클라이언트에게로 출력된다.
		return "Nice to meet you!";
	}
	

	
}
