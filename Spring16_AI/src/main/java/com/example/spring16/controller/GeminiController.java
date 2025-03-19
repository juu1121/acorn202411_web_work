package com.example.spring16.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring16.service.GeminiService;

import reactor.core.publisher.Mono;

@RestController
public class GeminiController {
	/*
	 * POST방식 /ask 요청을 하면서 아래의 형식과 같은 json 문자열을 전송하면 된다.
	 * { "prompt" : "질문" }
	 */
	
	@Autowired GeminiService service;
	
	@PostMapping("/ask") //Mono는 바로 리턴이 되지만 비동기로동작, 그래서Mono쓰는거임!
	public Mono<String> ask(@RequestBody Map<String, String> request){
		//질문 얻어내기
		String prompt = request.get("prompt");
		//서비스를 이용해서 질문에 대한 답을 리턴한다.
		return service.getChatResponse(prompt);
		
	}
	
	@PostMapping("/food") //Mono는 바로 리턴이 되지만 비동기로동작, 그래서Mono쓰는거임!
	public Mono<String> food(@RequestBody Map<String, String> request){
		//질문 얻어내기
		String prompt = request.get("prompt");
		//서비스를 이용해서 질문에 대한 답을 리턴한다.
		return service.getFoodCategory(prompt);
		
	}
}
