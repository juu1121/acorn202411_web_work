package com.example.spring02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SendController {
	/*
	 * 컨트롤러 메소드안에서 HttpServletRequest, HttpServletResponse, HttpSession등의 객체가 필요하면
	 * 매개변수안에 선언하면된다.
	 * 
	 * 선언만하면 spring프레임워크가 알아서 해당객체의 참조값을 매개변수에 전달해준다.
	 */
	@ResponseBody //리턴한걸출력
	@PostMapping("/send")  //post방식이니까
	public String send(HttpServletRequest request) {
		//요청 파라미터추출!
		String msg = request.getParameter("msg");
		//콘솔창에 출력하기
		System.out.println(msg);
		
		return "/send okay!";
	}
	
	// 전송되는 파라미터명과 동일하게 매개변수를 선언하면 자동으로 추출되어서 매개변수에 전달된다.
	// <input type="text" name="msg"
	@ResponseBody //리턴한걸출력
	@PostMapping("/send2")  
	public String send2(String msg) {
		System.out.println(msg);
		return "/send2 okay!";
	}
}
