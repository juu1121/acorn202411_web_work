package com.example.spring17.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.spring17.anno.SocketController;
import com.example.spring17.anno.SocketMapping;
import com.example.spring17.dto.ChatMessage;
import com.example.spring17.handler.SocketSessionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@SocketController
public class ChatSocketController {
	@Autowired
	private SocketSessionManager sessionManager;
	
	//객체 <=> json 상호 변경할 수 있는 객체
	ObjectMapper mapper = new ObjectMapper();
	
	@SocketMapping("/chat/whisper")
	public void chatWhisper(WebSocketSession session, ChatMessage message) {
		Map<String, Object> map = Map.of(
			"type","whisper",
			"payload",Map.of(
				"userName", message.getUserName(),
				"text", message.getText(),
				"toUserName", message.getToUserName()
			)
		);
		String json = "{}";
		try {
			json=mapper.writeValueAsString(map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		TextMessage msg = new TextMessage(json);
		//위의 TextMessage 를 보낸사람과 받는 사람에게 private 전송한다.
		sessionManager.privateMessage(message.getUserName(), msg);
		sessionManager.privateMessage(message.getToUserName(), msg);
	}
	
	@SocketMapping("/chat/public")
	public void chatPublic(WebSocketSession session, ChatMessage message) { 
		//누가 어떤 메세제를 보냈는지 json 에 담김! => 브로드캐스트메소드를 이용해 클라이언트에게 전달!
		String json = """
			{
				"type":"public",
				"payload":{
					"userName":"%s",
					"text":"%s"
				}
			}
		""".formatted(message.getUserName(), message.getText());
		TextMessage msg = new TextMessage(json);
		sessionManager.broadcast(msg);
	}
	
	@SocketMapping("/chat/enter")
	public void chatEnter(WebSocketSession session, ChatMessage message) {
		//대화방에 입장하는 userName
		String userName = message.getUserName();
		//누가 어떤 session 으로 입장했는지 저장하기 
		sessionManager.enterUser(userName, session);
		
//		//대화방 사용자 목록을 List<String> 으로 얻어내기
//		Map<String, WebSocketSession> userSessions = sessionManager.getAllUserSession();
//		//Map에 있는 모든 key(userName) 값을 Set<String> 으로 얻어내기
//		Set<String> keySet = userSessions.keySet(); //key로 만 이루어진 set를 얻어낸다(순서가 없는 묶음데이터)
//		//Set에 들어있는 내용을 이용해서 List 얻어내기
//		List<String> userList = new ArrayList<String>(keySet); //여기에 전달하면 순서가 있는 List로 변경
		
		//대화방에 입장한 모든 사용자 목록
		List<String> userList = sessionManager.getAllUserNames();
		
		Map<String, Object> map = Map.of(
			"type","enter",
			"payload", Map.of(
				"username", userName,
				"userList", userList
			)
		);
		//ObjectMapper 객체를 이용해 Map에 담긴 내용을 json 문자열로 변환 
		String json = "{}";
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
//		String json2 = """
//			{
//				"type":"enter",
//				"payload":{
//					"userName":"%s"
//					"userList":["kim", "lee", "park"]
//				}
//			}
//		""".formatted(userName); 
		
		//대화방에 입장한 모든 클라이언트에게 전송할 정보
		TextMessage msg = new TextMessage(json); //입장했는지, 누가입장했는지 알수있는 json문자열 클라이언트에게 전달
		
		//session Manager객체의 메소드를 이용해서 전송한다.
		sessionManager.broadcast(msg);
		
//		//session.sendMessage(msg); // 이 소켓세션은 입장한 사람만의 세션이다. so, 입장한사람에게만 문자열이 전달! => 모두에게 보내려면 반복문돌면서 모든 세션에 보내야함!
//		//대화방에 입장한 모든 세션에 동일한 메세지 보내기
//		Map<String, WebSocketSession> map=sessionManager.getAllUserSession();
//		for(String key : map.keySet() ) {
//			try {
//				map.get(key).sendMessage(msg);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}


}






