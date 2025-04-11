package com.example.spring17.controller;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//여기에 요청이 들어오려면 /api/image
@RequestMapping("/api")
@RestController
public class FileController {
	//업로드된 이미지를 저장할 위치 얻어내기 
	@Value("${file.location}") private String fileLocation;
	
	@PostMapping("/image")
	public Map<String, Object> uploadImage(MultipartFile image){
		String saveFileName=null;
		//만일 파일이 업로드 되지 않았다면
		if(!image.isEmpty()) {
			//원본 파일명 
			String orgFileName = image.getOriginalFilename();
			//이미지의 확장자를 유지하기 위해 뒤에 원본 파일명을 추가한다 
			saveFileName=UUID.randomUUID().toString()+orgFileName;
			//저장할 파일의 전체 경로 구성하기
			String filePath=fileLocation + File.separator + saveFileName;
			try {
				//업로드된 파일을 저장할 파일 객체 생성
				File saveFile=new File(filePath);
				image.transferTo(saveFile);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return Map.of("saveFileName", saveFileName);
	}
}
