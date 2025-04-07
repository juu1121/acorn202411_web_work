package com.example.spring17.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.spring17.handler.MySocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
	//웹소켓 핸들러(MyWebSocketHandler)를 등록하는 메소드
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// "/ws" 경로의 웹소캣 연결을 해 오면 MySocketHandler 객체로 처리를 하겠다는 의미 
		registry.addHandler(new MySocketHandler(), "/ws")
			.setAllowedOrigins("*"); //개발 중엔 CORS허용
	}

}
