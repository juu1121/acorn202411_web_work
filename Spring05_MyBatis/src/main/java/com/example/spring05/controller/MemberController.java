package com.example.spring05.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.spring05.dto.MemberDto;
import com.example.spring05.repository.MemberDao;

@Controller
public class MemberController {
	
	@Autowired //spring bean container로부터 주입(DI)받아서, 사용되야함!
	private MemberDao dao;
	
	@GetMapping("/member/delete")
	public String delete(int num) {
		dao.delete(num);
		return "redirect:/member/list";
	}
	
	@PostMapping("/member/update")
	public String update(MemberDto dto) {
		dao.update(dto);
		return "member/update";
	}
	
	@GetMapping("/member/edit")
	public String edit(int num, Model model) {
		//GET 방식 파라미터로 전달되는 회원의 번호를 이용해서 회워정보를 얻어온다.
		MemberDto dto = dao.getData(num);	
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
		dao.insert(dto);
		return "member/insert";
	}
	
	@GetMapping("/member/new")
	public String newForm() {
		
		return "member/new";
	}
	
	@GetMapping("/member/list")
	public String list(Model model) {
		//주입받은 MemberDao 객체를 이용해서 회원목록을 얻어온다.
		List<MemberDto> list = dao.getList();
		//model객체에 담는다.
		model.addAttribute("list", list);
		//Thymeleaf view페이지에서 회원 목록을 응답한다.
		return "member/list";
	}
}
