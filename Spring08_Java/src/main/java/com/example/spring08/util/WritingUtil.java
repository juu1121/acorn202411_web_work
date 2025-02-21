package com.example.spring08.util;

import org.springframework.stereotype.Component;

@Component
public class WritingUtil {
	public void writeLetter() {
		
		System.out.println("편지를 써요");	
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
		
	}
	
	public void writeReport() {
		
		System.out.println("보고서를 써요");
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
		
	}
	public void writeDiary() {
		
		System.out.println("일기를 써요");	
		try {
			Thread.sleep(1000);
		}catch(Exception e) {}
		
	}	
}
