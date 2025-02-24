package com.example.spring10.exception;

/*
 *	비밀번호 수정시 입력한 비밀번호가 DB에 저장된 비밀번호와 일치하지 않을떄 발생시키는 Exception type
 */
public class PasswordException extends RuntimeException {
	public PasswordException(String message) {
		super(message); //생성자의 매개변수에 전달된 예외메세지를 부모 생성자에 전달하면
		
		//이 객체의 .getMessage()메소드를 어딘가에서 호출하면 해당 메세지가 리턴된다.
		//.getMessage()메소드 -> 부모의 누군가가 가지고있다.
	}
}
