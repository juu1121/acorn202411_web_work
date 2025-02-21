package com.example.spring08.aop;


import java.util.Date;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/*
 * 횡단 관심사를 따로 클래스를 만들어서 작성한다.
 */
@Aspect //관심사는 @Aspect 어노테이션을 이용해서 만든다.
@Component // bean으로 만들기 위해 
public class TimeAspect {
	
	//@Before("execution(void writeLetter())")  // void writeLetter()모양의메소드가 실행되기 직전
	//리턴타입이 void면서 write로 시작하는 모든 메소드가 실행되기 직전 //매개변수에는 전달되는게 없어야함!
	@Before("execution(void write*())") 
	public void start() {
		Date start=new Date();
		long startTime = start.getTime();
		System.out.println("시작시간:"+startTime);
	}
	
	@After("execution(void write*())")
	public void end() {
		Date start=new Date();
		long startTime = start.getTime();
		System.out.println("종료시간:"+startTime);
	}	
}
