package com.example.spring07.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.spring07.dto.FileDto;

@Controller
public class FileController {
	/* custom.properties 파일이 로딩되기 위해서는 main메소드가 있는 클래스에
	 * @PropertiesSource 어노테이션 설정이 되어있어야한다.
	 * 
	 * custom.properties 파일에 있는 파일 업로드
	 * 경로를 읽어와서 필드에 담는다. 
	 */
	
	@Value("${file.location}") // 해당키값으로 저장한 내용을 읽어와서 필드에 넣어준다!
	private String fileLocation;
	
	/*ResponseEntity<InputStreamResource>는 파일을 다운로드할때 사용하는 리턴type이다.*/
	@GetMapping("/file/download")
	public ResponseEntity<InputStreamResource> download(String orgFileName, String saveFileName, long fileSize){
		//원래는 DB 에서 읽어와야 하지만 지금은 다운로드해줄 파일의 정보가 요청 파라미터로 전달된다.
		try {
			//다운로드 시켜줄 원본 파일명
			String encodedName=URLEncoder.encode(orgFileName, "utf-8"); //원본파일명을 인코딩=한글떄문에
			//파일명에 공백이 있는경우 파일명이 이상해지는걸 방지
			encodedName=encodedName.replaceAll("\\+"," ");
			//응답 헤더정보(스프링 프레임워크에서 제공해주는 클래스) 구성하기 (웹브라우저에 알릴정보)
			HttpHeaders headers=new HttpHeaders();
			//파일을 다운로드 시켜 주겠다는 정보 //octet-stream 파일을 전송하는 특별한 의미를가진 형식..!(서버가 클라이언트한테, body에 있는 정보를 전달!=>이게 다운로드임!)
			headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream"); 
			//파일의 이름 정보(웹브라우저가 해당정보를 이용해서 파일을 만들어 준다)
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+encodedName);
			//파일의 크기 정보도 담아준다.
			headers.setContentLength(fileSize);
			
			//읽어들일 파일의 경로 구성 //빨대꽂을위치!
			String filePath=fileLocation + File.separator + saveFileName;	
			
			//파일에서 읽어들일 스트림객체   //파일을.. InputStreamResource로 변환?
			InputStream is = new FileInputStream(filePath);
			//InputStreamResource 객체의 참조값 얻어내기
			InputStreamResource isr=new InputStreamResource(is);
			
			//ResponseEntity 객체를 구성해서
			ResponseEntity<InputStreamResource> resEntity=ResponseEntity.ok()
					.headers(headers)
					.body(isr);
			//리턴해주면 파일이 다운로드 된다.
			return resEntity;
			
		}catch(Exception e) {
			//예외정보를 콘솔에 출력
			e.printStackTrace();
			//예외 발생시키기
			throw new RuntimeException("파일을 다운로드 하는중에 에러 발생!");
		}
	}
	
	/*
	 * <input type="file" name="myFile">
	 * 은 MultiparFile myFile로 해서 업로드된 파일의 정보를 얻어내야 한다.
	 */
	@PostMapping("/file/upload") 
	public String upload(String title, MultipartFile myFile, Model model) {
		
		//만일 파일이 업로드되지않았다면
		if(myFile.isEmpty()) {
			throw new RuntimeException("파일이 업로드되지않았습니다.");
		}
		
		//원본파일명
		String orgFileName=myFile.getOriginalFilename();
		System.out.println("orgFileName: "+ orgFileName);
		//파일의크기
		long fileSize = myFile.getSize();
		System.out.println("fileSize: "+ fileSize);
		
		//저장할 파일의 이름을 Universal Unique 한 문자열로 얻어내기
		String saveFileName=UUID.randomUUID().toString();
		System.out.println("saveFileName: "+ saveFileName);
		
		//저장할 파일의 전체경로 구성하기
		String filePath=fileLocation+File.separator+saveFileName;
		System.out.println("filePath: "+ filePath);
		
		try {
			//업로드 된 파일을 저장할 파일 객체 생성
			File saveFile=new File(filePath); //
			myFile.transferTo(saveFile); //업로드된파일을 saveFile에 저장?
			//MultipartFile.transferTo(File dest) 메서드는 MultipartFile 객체로 업로드된 파일을 지정된 위치(dest)로 저장하는 기능
		}catch(Exception e) {
			e.printStackTrace();
		}
		//원래는 DB에 저장해야 하지만 테스트를 위해 view Page에 전달해서 출력한다.
		model.addAttribute("title", title);
		model.addAttribute("orgFileName", orgFileName);
		model.addAttribute("fileSize", fileSize);
		model.addAttribute("saveFileName", saveFileName);
		
		
		return "file/upload";
	}

	
	@PostMapping("/file/upload2") 
	public String upload2(FileDto dto, Model model) {
		//FileDto 객체에서 MultipartFile객체를 얻어낸다.
		MultipartFile myFile = dto.getMyFile();
		
		//만일 파일이 업로드되지않았다면
		if(myFile.isEmpty()) {
			throw new RuntimeException("파일이 업로드되지않았습니다.");
		}
		
		//원본파일명
		String orgFileName=myFile.getOriginalFilename();
		//파일의크기
		long fileSize = myFile.getSize();
		//저장할 파일의 이름을 Universal Unique 한 문자열로 얻어내기
		String saveFileName=UUID.randomUUID().toString();
		//저장할 파일의 전체경로 구성하기
		String filePath=fileLocation+File.separator+saveFileName;
		try {
			//업로드 된 파일을 저장할 파일 객체 생성
			File saveFile=new File(filePath);
			myFile.transferTo(saveFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//원래는 DB에 저장해야 하지만 테스트를 위해 view Page에 전달해서 출력한다.
		model.addAttribute("title", dto.getTitle());
		model.addAttribute("orgFileName", orgFileName);
		model.addAttribute("fileSize", fileSize);
		model.addAttribute("saveFileName", saveFileName);
		
		
		return "file/upload";
	}

	
	
	@GetMapping("/file/new")
	public String newForm() {
		
		return "file/new";
	}

}
