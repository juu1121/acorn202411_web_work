package com.example.spring05.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.spring05.dto.MemberDto;
import com.example.spring05.dto.TodoDto;
import com.example.spring05.repository.TodoDao;

@Controller
public class TodoController {
	@Autowired // dao주입
	private TodoDao dao;

	@GetMapping("/todo/delete")
	public String delete(int id) {
		dao.delete(id);
		return "redirect:/todo/list";
	}	
	
	//update
	@PostMapping("/todo/update")
	public String update(TodoDto dto) {
		dao.update(dto);
		return "todo/update";
	}
	
	//수정폼
	@GetMapping("/todo/edit")
	public String edit(int id, Model model) {
		TodoDto dto = dao.getData(id);
	
		//응답에 필요한 데이터를 Model객체에 담는다
		model.addAttribute("dto", dto);
		
		return "todo/edit";
	}	
	
	//insert
	@PostMapping("/todo/insert")
	public String insert(TodoDto dto) {
		dao.insert(dto);
		return "todo/insert";
	}	
	
	//추가 폼
	@GetMapping("/todo/new")
	public String newForm() {
		return "todo/new";
	}	
	
	//리스트목록
	@GetMapping("/todo/list")
	public String list(Model model) {
		List<TodoDto> list = dao.getList();
		model.addAttribute("list", list);
		return "todo/list";
	}
	
}
