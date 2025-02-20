package com.example.spring06.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.spring06.dto.MemberDto;
import com.example.spring06.repository.MemberDao;
import com.example.spring06.service.MemberService;

@Controller
public class MemberController {
	//서비스 객체는 Controller에서 활용하는 유틸리티라고 생각하면 된다.
	@Autowired 
	private MemberService service;
	
	@GetMapping("/member/delete")
	public String delete(int num) {
		service.deleteById(num);
		return "redirect:/member/list";
	}
	
	@PostMapping("/member/update")
	public String update(MemberDto dto) {
		service.update(dto);
		return "member/update";
	}
	
	@GetMapping("/member/edit")
	public String edit(int num, Model model) {
		//서비스를 이용해서 회원정보를 얻어온다.
		MemberDto dto = service.findById(num);	
		//응답에 필요한 데이터를 Model객체에 담는다
		model.addAttribute("dto", dto);
		
		return "member/edit";
	}
	
	/*
	 * 매개변수에 MemberDto type을 선언하면 form 전송되는 파라미터가 자동추출되어서
	 * MemberDto 객체에 담긴채로 참조값이 전달된다.
	 * form의 name속성와 MemberDto's 필드명이 동일해야한다.
	 */
	@PostMapping("/member/insert")
	public String insert(MemberDto dto) {
		//서비스를 이용해서 저장하기
		service.save(dto);
		
		return "member/insert";
	}
	
	@GetMapping("/member/new")
	public String newForm() {
		
		return "member/new";
	}
	
	@GetMapping("/member/list")
	public String list(Model model) {
		//서비스를 이용해서 회원목록을 얻어온다.
		List<MemberDto> list = service.findAll();
		//model객체에 담는다.
		model.addAttribute("list", list);
		//Thymeleaf view페이지에서 회원 목록을 응답한다.
		return "member/list";
	}
}
