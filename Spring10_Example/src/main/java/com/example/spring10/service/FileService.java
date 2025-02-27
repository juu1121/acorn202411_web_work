package com.example.spring10.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.spring10.dto.FileDto;


public interface FileService  {
	public List<FileDto> getFiles();
	public void createFile(FileDto dto);
	public FileDto getByNum(long num); //파일하나의 정보
	public ResponseEntity<InputStreamResource> download(long num);
}	