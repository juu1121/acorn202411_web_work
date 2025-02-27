package com.example.spring10.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileDto {
	private long num;
	private String uploader;
	private String title;
	private String orgFileName;
	private String saveFileName;
	private long fileSize;
	private String uploadedAt;
	//파일 업로드 폼에 있는 input type="file"의 name속성의 value와 필드명이 일치해야한다.
	//<input type="file" name="myFile"/>
	private MultipartFile myFile; 
}
