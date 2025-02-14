package com.example.spring01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.spring01.service.Drill;
import com.example.spring01.service.DrillImpl;
import com.example.spring01.service.HomeService;
import com.example.spring01.service.HomeServiceImpl;

import jakarta.annotation.PostConstruct;

/*
 *  @SpringBootApplication 어노테이션이 많은 기능을 하는데
 *  그중에 하나는 이 클래스 (Spring01HelloApplication)클래스로 객체를 생성해서 관리를 해준다.
 * */
@SpringBootApplication
public class Spring01HelloApplication {
	//Spring01HelloApplication 객체 안에서 필요한 객체가 있다면 필드를 선언하고 
	//@Autowired 어노테이션을 붙어놓으면 해당 객체가 주입(DI)된다.
	
	@Autowired //필드선언하면 자동주입된다.
	HomeService service;  //원래 필드는 선언하면 null인데,(@Autowired 어노테이션을 붙어놓으면 해당 객체가 주입(DI)된다.)=> 스프링빈컨테이너에 저 타입객체가 있으면 주입된다고!(의존객체는 무조건은 아니고, bean들끼리만 주입된다.)
	
	//이 클래스로 객체가 생성된 이후에 호출하고싶은 메소드에  @PostConstruct를 붙여놓으면된다(자동생성)
	@PostConstruct  
	public void exec() {
		System.out.println("exec() 메소드 호출됨");
		service.clean("맹구");
		
		//맹구의 빨래를 하고 싶으면?
		service.wash("맹구");
		
		service.hole("바닥");
	}

	public static void main(String[] args) {
		//run() 메소드가 리턴하는 객체 (이 객체를 Spring Bean Container라고 생각하면 된다.)
		ApplicationContext context = SpringApplication.run(Spring01HelloApplication.class, args);
		
		//"원숭이"의 집을 청소해야한다면 어떻게 해야할까?
		//HomeServiceImpl service1 = new HomeServiceImpl();
		//service1.clean("원숭이");
		
		//spring이 관리하는 객체중에 HomeService type객체를 얻어낸다.
		HomeService service2 = context.getBean(HomeService.class);
		service2.clean("원숭이");
		
		
		
	}

}
