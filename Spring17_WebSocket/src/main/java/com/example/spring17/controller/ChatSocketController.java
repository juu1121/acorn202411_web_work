package com.example.spring17.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.spring17.anno.SocketController;
import com.example.spring17.anno.SocketMapping;
import com.example.spring17.dto.ChatMessage;
import com.example.spring17.handler.SocketSessionManager;

import lombok.Data;

@SocketController
public class ChatSocketController {
	@Autowired
	private SocketSessionManager sessionManager;
	
	@SocketMapping("/chat/enter")
	public void enter(WebSocketSession session, ChatMessage message) {
		//대화방에 입장하는 userName
		String userName = message.getUserName();
		//누가 어떤 session 으로 입장했는지 저장하기 
		sessionManager.enterUser(userName, session);
		String json = """
			{
				"type":"enter",
				"payload":{
					"userName":"%s"
				}
			}
		""".formatted(userName); 
		
		TextMessage msg = new TextMessage(json); //입장했는지, 누가입장했는지 알수있는 json문자열 클라이언트에게 전달
		
		//session.sendMessage(msg); // 이 소켓세션은 입장한 사람만의 세션이다. so, 입장한사람에게만 문자열이 전달! => 모두에게 보내려면 반복문돌면서 모든 세션에 보내야함!
		
		//대화방에 입장한 모든 세션에 동일한 메세지 보내기
		Map<String, WebSocketSession> map=sessionManager.getAllUserSession();
		for(String key : map.keySet() ) {
			try {
				map.get(key).sendMessage(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@SocketMapping("/chat/send")
	public void sendMessage(WebSocketSession session, ChatMessage message) {
		//전달된 대화내용을 TextMessage 객체에 담는다.
		TextMessage msg = new TextMessage(message.getText());
		//sessionManager 객체를 이용해서 접속된 모든 세션에 TextMessage를 전달한다. 
		//sessionManager.getSessions()여기서 관리되고있음
		sessionManager.getSessions().forEach((item)->{
			//item은 WebSocketSession객체이다.
			try {
				item.sendMessage(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}






