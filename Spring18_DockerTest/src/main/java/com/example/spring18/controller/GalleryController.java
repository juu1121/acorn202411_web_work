package com.example.spring18.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring18.dto.GalleryDto;

@Controller
public class GalleryController {
	@Value("${file.location}")
	private String fileLocation;
	
	@PostMapping("/gallery/upload")
	public String upload(GalleryDto dto, Model model) {
		
		// @Data가 오버라이드 한 toString()메소드를 확인하기 위해
		//원래는 dto객체의 hash값이 출력되야 하지만 필드를 확인할수있는 문자열이 출력된다.
		System.out.println(dto); 
		
		//MultipartFile 객체
		MultipartFile image = dto.getImage();
		//만일 파일이 업로드되지않았다면
		if(image.isEmpty()) {
			throw new RuntimeException("이미지가 업로드되지않았습니다.");
		}
		//원본파일명
		String orgFileName=image.getOriginalFilename();
		System.out.println("orgFileName"+orgFileName); 
		
		//저장할 파일의 이름을 Universal Unique 한 문자열로 얻어내기
		//이미지 확장자를 위해 뒤에 원본파일명도 추가한다.
		String saveFileName=UUID.randomUUID().toString()+orgFileName;
		System.out.println("saveFileName"+saveFileName); 
		
		//저장할 파일의 전체경로 구성하기
		String filePath=fileLocation+File.separator+saveFileName;
		System.out.println("filePath"+filePath);
		
		try {
			//업로드 된 파일을 저장할 파일 객체 생성
			File saveFile=new File(filePath);
			image.transferTo(saveFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//원래는 DB에 저장해야 하지만 테스트를 위해 Model에 담아서 응답한다.
		model.addAttribute("caption", dto.getCaption());
		model.addAttribute("saveFileName", saveFileName);
		
		return "gallery/upload";
	}
	
	@GetMapping("/gallery/new")
	public String newForm() {
		
		return "gallery/new";
	}
}
