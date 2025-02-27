package com.example.spring10.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring10.dto.FileDto;
import com.example.spring10.service.FileService;

@Controller
public class FileController {

	@Autowired 
	private FileService service;

	@Value("${file.location}") // 해당키값으로 저장한 내용을 읽어와서 필드에 넣어준다!
	private String fileLocation;	

	
	@GetMapping("/file/list")
	public String list(Model model) {
		List<FileDto> list = service.getFiles();
		
		model.addAttribute("list", list);

		return "file/list";
	}
	
	@PostMapping("/file/upload") 
	public String upload(FileDto dto) { //title랑myfile가 있음
		service.createFile(dto);
		
		return "file/upload";
	}	
	
	@GetMapping("/file/new")
	public String newForm(){
		return "file/new";
	}
	
	@GetMapping("/file/download")
	public ResponseEntity<InputStreamResource> download(long num) {
		
		return service.download(num);
	}
	
	
}
