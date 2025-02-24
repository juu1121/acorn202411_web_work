package com.example.spring10;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;

//파일 업로드 위치 정보를 가지고 ㅣㅇㅆ는 custom.properties 파일을 로딩시키기 위한 어노테이션
@PropertySource(value="classpath:custom.properties") //내가 설정한 properties문서를 스프링부트가 읽는다.
@SpringBootApplication
public class Spring10ExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring10ExampleApplication.class, args);
	}
	
	//서버가 준비 되었을때 실행할 메소드 설정
	@EventListener(ApplicationReadyEvent.class)
	public void openChrome() {
		String url = "http://localhost:9000/spring10/";
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
