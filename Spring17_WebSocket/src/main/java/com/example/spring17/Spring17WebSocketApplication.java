package com.example.spring17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

//파일 업로드 경로가 설정되어있는 properties 파일 로딩하기
@PropertySource("classpath:custom.properties")
@SpringBootApplication
public class Spring17WebSocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring17WebSocketApplication.class, args);
	}

}
