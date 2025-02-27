package com.example.spring10.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.spring10.dto.CommentDto;
import com.example.spring10.dto.PostDto;
import com.example.spring10.exception.DeniedException;
import com.example.spring10.repository.CommentDao;
import com.example.spring10.repository.PostDao;

@Component // bean으로 만들기위한 어노테이션
@Aspect //aspect 역할을 하기위한 어노테이션 ..횡단관심사였나...
public class AuthAspect {
	
	@Autowired private PostDao postDao;
	@Autowired private CommentDao commentDao;

	//매개변수에 전달된 값을 사용해야하기때문에 around사용! => before나 after는 joinpoint가 제공되지않기때문이다!!
	@Around("execution(* com.example.spring10.service.*.delete*(..)) || execution(* com.example.spring10.service.*.update*(..))")  
	//리턴은 다 허용 /서비스패키지의 /모든클래스에서/ delete로시작하는 모든메소드/매개변수는 가리지않는다.
	public Object checkWriter(ProceedingJoinPoint joinPoint) throws Throwable {

		//aop가 적용된 메소드명 알아내기
		String methodName=joinPoint.getSignature().getName();
		System.out.println(methodName + "메소드에 aop가 적용됨");
		
		//수정, 삭제 작업을 할 글번호
		long num=0;
		
		//매개변수에 전달된 데이터를 Object[]로 얻어내기
		Object[] args = joinPoint.getArgs();
		//반복문 돌면서 원하는 type을 찾는다.
		for(Object tmp:args) {
			if(tmp instanceof Long) {
				num=(long)tmp; //캐스팅
			}else if(tmp instanceof PostDto) {
				num=((PostDto)tmp).getNum();
			}else if(tmp instanceof CommentDto) {
				num=((CommentDto)tmp).getNum();
			}
		}
		String writer = null;
		if(methodName.contains("Post")) {
			writer = postDao.getData(num).getWriter();
		}else if(methodName.contains("Comment")) {
			writer=commentDao.getData(num).getWriter();
		}
		//로그인된 userName
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();		
		if(!writer.equals(userName)) {
			throw new DeniedException("남의 글 지우려고하면 안돼영");
		}	
		//aop가 적용된 메소드를 실행한다.
		Object obj = joinPoint.proceed();	
		return obj;
	}
}
