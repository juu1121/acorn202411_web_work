package com.example.spring01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/*
 * 스프링 프레임 워크가 HomeServiceImpl 클래스가 존재하는 패키지(com.example.spring01.service)를
 * component scan을 하게 되면 적절한 어노테이션(@Component)이 붙어 있는 
 * 이 클래스로 객체를 생성해서 해당 객체의 참조값을 spring bean container에서 관리하게 된다.
 *  
 *  - 어디를 scan하게 되나?
 *    @SpringBootApplication 어노테이션이 붙어있는 클래스가 존재하는 패키지 혹은 하위 패키지를 모두 scan 한다.
 */
@Component
public class HomeServiceImpl implements HomeService{
	
	//의존객체 주입받기(DI) //의존객체는 무조건은 아니고, bean들끼리만 주입된다.
	@Autowired
	Drill drill;
	
	//생성자
	public HomeServiceImpl() {
		System.out.println("HomeServiceImpl 객체가 생성됨");
	}

	@Override
	public void clean(String name) {
		System.out.println(name+"의 집을 청소해요!");
	}

	@Override
	public void wash(String name) {
		System.out.println(name+"의 빨래를 빨아요~");
	}

	@Override
	public void hole(String name) {
		System.out.println(name+"에 구멍을 뚫어요");
		drill.hole();
	}

}
