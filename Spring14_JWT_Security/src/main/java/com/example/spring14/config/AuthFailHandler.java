package com.example.spring14.config;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//로그인 실패시 동작하는 핸들러
@Component
public class AuthFailHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		//발생한 예외의 종류를 확인해서 에러 메세지를 설정한다.
		//아래 4개의 예외는 AuthenticationException가 부모타입이다!
		String errorMessage = "";
        if (exception instanceof BadCredentialsException) {
        	errorMessage = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
        	errorMessage = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof DisabledException) {
        	errorMessage = "계정이 비활성화되었습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof CredentialsExpiredException) {
        	errorMessage = "비밀번호 유효기간이 만료 되었습니다. 관리자에게 문의하세요.";
        }		
        
        //에러 메세지를 request에 담고
        request.setAttribute("errorMessage", errorMessage);
        //로그인 폼으로 forward 이동해서 응답한다. 
        /*
         * 그런데 이 요청은 POST방식 /user/login 요청에 대한 응답이다.
         * 따라서 forward 이동될 /user/loginform은 GET, POST 방식 요청을 모두 처리 할 수 있어야한다.
         */
        request.getRequestDispatcher("/user/loginform").forward(request, response);
	}

}
