package com.example.spring17.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @Target(ElementType.METHOD) => 메소드에 붙이는 어노테이션
 * 
 * String value();
 * 위의 선언을 하면 아래와 같은 형식으로 @SocketMapping()을 사용할수있다. 
 * @SocketMapping(value="/chat/send")
 * @SocketMapping("/chat/send") //(value는 생략가능)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketMapping {
	String value(); // SocketMapping어노테이션은 value를 전달받을수있고, string타입이다.
}
