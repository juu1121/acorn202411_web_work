package com.example.spring13.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.spring13.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{ //OncePerRequestFilter 추상클래스 상속받는다.
	@Autowired JwtUtil util;

	//쿠키에 저장된 token의 이름
	@Value("${jwt.name}") String jwtName;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwtToken = null;
		
		//쿠키에서 token 추출 하기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
        	//반복문 돌면서 
            for (Cookie cookie : cookies) {
            	// custum.properties 파일에 설정된  "jwtToken" 이라는 쿠키이름으로 저장된 value 가 있는지 확인해서
                if (jwtName.equals(cookie.getName())) {
                	//있다면 그 value 값을 지역변수에 담기 
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }		
		//요청의 Header에 "Authorization"이라는 키값으로 전달된 문자열이 있는지 읽어와본다.
		String authHeader=request.getHeader("Authorization");
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			//"Bearer "(공백포함7자리)를 제외한 뒤의 token문자열을 얻어낸다.
			jwtToken = authHeader.substring(7);
		}	
		//토큰이 존재하고
		if(jwtToken != null) {
			//유효성 검증을 통과한다면
			if(util.validateToken(jwtToken)) {
				//Spring Security에 1회성 로그인 처리를 해주고 요청의 흐름을 이어간다. 
				filterChain.doFilter(request, response);
				return;
			}
		}
		//에러를 응답한다.
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Auth Fail!");
	} 
}
