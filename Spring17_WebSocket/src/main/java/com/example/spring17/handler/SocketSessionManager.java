package com.example.spring17.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketSessionManager {
	// Thread Safe 한 동기화된 리스트 객체 사용하기 (웹소켓 접속한 모든 클라이언트의 session이 저장되어 있다.) 
	List<WebSocketSession> sessionList=Collections.synchronizedList(new ArrayList<>()); //이 리스트는 대화방과 상관없이, 모든 웹소켓세션이 들어있는 리스트!
	
	/*
	 * userName <=> SocketSession 를 저장하기 위한 Map
	 * ConcurrentHashMap 객체도 Threa Safe한 동기화된 Map객체이다. => 여러클라이언트가 동시에 사용하니까 스레드 안정성이 보장되어야한다.
	 * (ConcurrentHashMap는 "대화방에 입장한" 모든 클라이언트의 session과 userName이 저장되어있다.) 
	 */
	//ConcurrentHashMap여기에 누가 어떤이름으로 접속했는지 저장을했는데, 세션을 이용해서 userName을 찾을때를 대비해서 두 객체를 관리함
	Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>(); //userName으로 소켓찾기
	Map<WebSocketSession, String> sessionUsers = new ConcurrentHashMap<>(); //소켓아이디를 이용해서(소켓을 이용해서) userName찾기
	//객체 <=> json 상호 변경할 수 있는 객체
	ObjectMapper mapper = new ObjectMapper();
	
	
	// 대화방에 참여한 모든 userName목록을 리턴하는 메소드
	public List<String> getAllUserNames(){
		//Map에 있는 모든 key(userName) 값을 Set<String> 으로 얻어내기
		Set<String> keySet = userSessions.keySet(); //key로 만 이루어진 set를 얻어낸다(순서가 없는 묶음데이터)
		//Set에 들어있는 내용을 이용해서 List 얻어내기
		List<String> userList = new ArrayList<String>(keySet); //여기에 전달하면 순서가 있는 List로 변경
		return userList;
	}
	
	//대화방에 참여한 user의 session 을 저장하는 메소드
	public void enterUser(String userName, WebSocketSession session) {
		userSessions.put(userName, session);
		sessionUsers.put(session, userName);
	}
	
	//userName를 전달하면 해당 Session을 리턴해주는 메소드
	public WebSocketSession getUserSession(String userName) {
		return userSessions.get(userName);
	}
	
	//session을 전달하면 해당 session을 사용하는 userName을 리턴해주는 메소드
	public String getSessionUser(WebSocketSession session) {
		return sessionUsers.get(session);
	}
	
	//모든 user session 정보를 리턴하는 메소드
	public Map<String, WebSocketSession> getAllUserSession(){
		return userSessions;
	}
	//userName을 Map 에서 제거하는 메소드 //userName을 전달하면 제거하는 메소드!
	public void removeUser(String userName) {
		//제거하고 제거된 session 이 리턴된다.
		WebSocketSession removedSession=userSessions.remove(userName);
		//sessionUsers에서도 session을 이용해서 해당 정보를 제거하기
		sessionUsers.remove(removedSession);
		 
		//누가 퇴장 했는지 TextMessage 를 방에 입장한 모든 클라이언트에게 전송한다.
		Map<String, Object> map = Map.of(
			"type","leave",
			"payload", Map.of(
				"userName",userName 
			)
		);
		String json = "{}";
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map); 
			//writerWithDefaultPrettyPrinter를 쓰면 개행기호 들여쓰기도 된다
			//writeValueAsString 이것만쓰면 한줄로 json 출력
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
//		formatted을 사용해서 json 만들기
//		String json2="""
//			{
//				"type":"leave",
//				"payload":{
//					"userName":"%s"
//				}
//			}
//		""".formatted(userName);
		
		
		TextMessage msg = new TextMessage(json);
		broadcast(msg);
	}
	
	//이 메소드를 출하면서 메세지를 전달하면, 대화방에 접속한 모든 클라이언트들에게 메세지가 보내짐! 
	//대화방에 입장한 모든 session에 TextMessage를 중계하는 메소드 
	public void broadcast(TextMessage msg) {
		//sessionUsers는 map이다. forEach에 함수를 넣어주면서 매개변수에 key와 value를 넣어준다. 
		//Map에 저장된 모든 key, value를 순회하면서
		sessionUsers.forEach((key, value)->{
			//key:session, value:userName
			try {
				key.sendMessage(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void register(WebSocketSession session) {
		sessionList.add(session);
	}
	
	public void remove(WebSocketSession session) {
		sessionList.remove(session);
	}
	
	public List<WebSocketSession> getSessions(){
		return sessionList;
	}
	
}


