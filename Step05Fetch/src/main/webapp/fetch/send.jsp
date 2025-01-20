<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//GET방식 요청 파라미터 읽어오기
	String msg=request.getParameter("msg");
	System.out.println("msg:"+msg);
%>
메세지 잘 받았어 클라이언트야!

{
	"num":1,
	"name": "김구라",
	"inMan":true
}
문자열을 이렇게 작성하면
자바스크립트에서는 해당문자열을 그 모양의 객체로 변경가능!
오브젝트로 만들수있다면 -> .num해서 데이터를 가져올수있어

JSON.parse(data); 를 사용하면 data문자열이 JSON형식으로 변경된다!
첫번째 .then에 응답할데이터가 json문자열임을 명시하면, parse가 자동으로된다 