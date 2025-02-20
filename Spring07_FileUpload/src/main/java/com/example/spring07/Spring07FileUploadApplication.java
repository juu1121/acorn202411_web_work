package com.example.spring07;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
/*
 *  @PropertySource(value="커스텀 properties 파일의 위치")
 *  classpath:는 resources 폴더를 가리킨다.
 */
@PropertySource(value="classpath:custom.properties") //내가 설정한 properties문서를 스프링부트가 읽는다.
@SpringBootApplication
public class Spring07FileUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring07FileUploadApplication.class, args);
	}
	//서버가 준비 되었을때 실행할 메소드 설정
	@EventListener(ApplicationReadyEvent.class)
	public void openChrome() {
		String url = "http://localhost:9000/spring07/";
		// 운영체제의 얻어와서 이름을 소문자로 
		String os = System.getProperty("os.name").toLowerCase();
		ProcessBuilder builder = null;
		try {
			if (os.contains("win")) {
				builder = new ProcessBuilder("cmd.exe", "/c", "start chrome " + url);
			} else if (os.contains("mac")) {
				builder = new ProcessBuilder("/usr/bin/open", "-a", "Google Chrome", url);
			} else {
				System.out.println("지원 하지 않는 운영체제 입니다.");
				return;
			}
			builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
