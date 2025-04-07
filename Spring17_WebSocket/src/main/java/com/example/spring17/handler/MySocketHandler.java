package com.example.spring17.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MySocketHandler extends TextWebSocketHandler{
	//List<WebSocketSession> sessionList = new ArrayList<>();
	// Thread Safe한 동기화된 리스트 객체 사용하기 //synchronizedList : 여러스레드에서 접속해도 안전한 리스트
	List<WebSocketSession> sessionList = Collections.synchronizedList(new ArrayList<>()); 
	
	//클라이언트가 웹소켓 연결을 요청하고 성공되었을때 호출되는 메소드 
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String sessionId = session.getId();
		System.out.println(sessionId+"연결됨!");
		TextMessage message = new TextMessage("안녕 클라이언트야!");
		session.sendMessage(message);
		//접속된 클라이언트의 WebSocketSession을 List에 추가하기
		sessionList.add(session);
	}
	//클라이언트가 메세지를 보내면 호출되는 메소드 
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(message.getPayload());
		
		sessionList.forEach((item)->{
			//item은 WebSocketSession
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	//클라이언트의 웹소켓 연결이 종료되면 호출되는 메소드 
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String sessionId = session.getId();
		System.out.println(sessionId+"연결해제됨!");
		//연결해제된 클라이언트의 WebSocketSession 을 List 에서 찾아서 제거하기
		sessionList.remove(session); //인덱스가 아닌 참조값의 의한 삭제 - 알아서 찾아서 삭제해준다.
	}

}
