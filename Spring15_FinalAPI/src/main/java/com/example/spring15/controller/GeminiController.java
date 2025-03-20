package com.example.spring15.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring15.service.GeminiService;

import reactor.core.publisher.Mono;

@RestController
public class GeminiController {
	@Autowired private GeminiService service;
	
	@PostMapping("/gemini/quiz")
	public Mono<String> quiz(@RequestBody Map<String, String> map) { //map에는 quiz:문제, answer:답 이 담긴다.
		return service.quiz2(map);
	}
}
