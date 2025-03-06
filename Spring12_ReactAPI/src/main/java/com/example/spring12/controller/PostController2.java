package com.example.spring12.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring12.dto.PostDto;
import com.example.spring12.entity.Post;
import com.example.spring12.repository.PostRepository;
import com.example.spring12.service.PostService;
/*
	이 컨트롤러는 응답을 json으로 할거임 so, @ResponseBody가 모든 메소드에 필요
	@RestController에서는 @ResponseBody가 기본이다. 
	@RestController를 붙이면
	해당 컨트롤러는 리턴하는 모든데이터가 json으로 변경되어응답 //컨트롤러에서 리턴하는 문자열을 json형식으로 리턴한다.
	모든 리턴 값을 JSON으로 변환해주기때문에 so, 리턴하는건, Map, dto, List, int String 모두 될수있다.
*/
@RequestMapping("/v2")
@RestController
public class PostController2 {
	
	@Autowired PostService service;
	//@ResponseBody RestController에서는 @ResponseBody가 기본이다.
	/*
	 * 보통 API서버에는 클라이언트가 json문자열을 전송한다.
	 * 해당 json 문자열에서 데이터를 추출하기 위해서는 @RequestBody라는 어노테이션이 필요하다.
	 */
	
	@PostMapping("/posts")
	public PostDto insert(@RequestBody PostDto dto){

		return service.save(dto);
	}
	
	//글 목록 요청 처리
	@GetMapping("/posts")
	public List<PostDto> list(){

		return service.findAll();
	}
	
	@DeleteMapping("/posts/{id}")
	public PostDto delete(@PathVariable("id") long id) {

		return service.delete(id);
	}
	
	//post 전체필드수정 요청
	@PutMapping("/posts/{id}")
	public PostDto updateAll(@PathVariable("id") long id, @RequestBody PostDto dto) {
		//경로변수에 있는 수정할 글의 id를 dto에 넣어준다.
		dto.setId(id);
		
		return service.updateAll(dto);
	}
	
	//post 일부 수정 요청
	@PatchMapping("/posts/{id}")
	public PostDto update(@PathVariable("id") long id, @RequestBody PostDto dto) {
		dto.setId(id);
		return service.update(dto);
	}
	
	@GetMapping("/posts/{id}")
	public PostDto findPost(@PathVariable("id") long id) {

		return service.find(id);
	}
}















