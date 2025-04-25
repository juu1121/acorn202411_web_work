package com.example.spring18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value="classpath:custom.properties") //내가 설정한 properties문서를 스프링부트가 읽는다.
@SpringBootApplication
public class Spring18DockerTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring18DockerTestApplication.class, args);
		/*
		 * System클래스의 .getenv() 메소드를 이용하면 시스템 환경변수의 값을 얻어낼수있다
		 */
		String javaHome = System.getenv("JAVA_HOME");
		System.out.println("JAVA_HOME:"+javaHome);
	}

}
