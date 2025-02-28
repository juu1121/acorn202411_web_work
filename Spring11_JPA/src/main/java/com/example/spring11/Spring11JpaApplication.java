package com.example.spring11;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.example.spring11.entity.Member;
import com.example.spring11.repository.MemberRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Spring11JpaApplication {

	@Autowired MemberRepository memberRepo;
	
	@PostConstruct
	public void memberTest() {
		//DB에 저장할 Member entity객체를 생성해서
		Member m1 = Member.builder().name("김구라").addr("노량진").build();
		Member m2 = Member.builder().name("해골").addr("행신동").build();
		Member m3 = Member.builder().name("원숭이").addr("상도동").build();
		//DB에 저장하기
		memberRepo.save(m1);
		memberRepo.save(m2);
		memberRepo.save(m3);
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Spring11JpaApplication.class, args);
	}
	
	//서버가 준비 되었을때 실행할 메소드 설정
	@EventListener(ApplicationReadyEvent.class)
	public void openChrome() {
		String url = "http://localhost:9000/spring11/";
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
