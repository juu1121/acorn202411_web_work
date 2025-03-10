package com.example.spring14.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.CookieRequestCache;

import com.example.spring14.filter.JwtFilter;

import jakarta.servlet.http.Cookie;

@Configuration //설정클래스라고 알려준다.
@EnableWebSecurity //Security를 설정하기 위한 어노테이션
public class SecurityConfig {
	
	//jwt를 쿠키로 저장할때 쿠키의 이름
	@Value("${jwt.name}")
	private String jwtName;
	
	@Autowired
	private JwtFilter jwtFilter; 
	
	/*
	 *  매개변수에 전달되는 HttpSecurity 객체를 이용해서 우리의 프로젝트 상황에 맞는 설정을 기반으로 
	 *  만들어진 SecurityFilterChain 객체를 리턴해주어야 한다. (httpSecurity기반으로 만들어진 SecurityFilterChain 객체를 리턴)
	 *  또한 SecurityFilterChain 객체도 스프링이 관리하는 Bean 이 되어야 한다  
	 */
	@Bean //메소드에서 리턴되는 SecurityFilterChain 을 bean 으로 만들어준다.
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
			AuthSuccessHandler successHandler,
			CookieRequestCache cookCache) throws Exception{
		String[] whiteList= {"/", "/play", "/user/loginform", "/user/login-fail", "/user/expired"};
		
		httpSecurity
		.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(config ->
			config
				.requestMatchers(whiteList).permitAll() //최상위경로요청은 whiteList니까,, 다 받아들어라(permitAll)
				.requestMatchers("/admin/**").hasRole("ADMIN") //admin하위요청은 (hasRole)role이 admin이어야 요청할수있다.
				.requestMatchers("/staff/**").hasAnyRole("ADMIN", "STAFF") //staff하위요청은 (hasAnyRole)저 중에서 아무role이나 가지고있어도 요청가능하다
				.anyRequest().authenticated() //저거 이외의 어떤요청도 인증을 받아야한다 = 로그인해야한다(로그인해야 요청가능)
		)
		.formLogin(config ->
			config
				//인증을 거치지 않은 사용자를 리다일렉트 시킬 경로 설정 
				.loginPage("/user/required-loginform")
				//로그인 처리를 할때 요청될 url 설정 ( spring security 가 login 처리를 대신 해준다)
				.loginProcessingUrl("/user/login")
				//로그인 처리를 대신 하려면 어떤 파라미터명으로 username 과 password 가 넘어오는지 알려주기 
				.usernameParameter("userName")
				.passwordParameter("password")
				.successHandler(new AuthSuccessHandler()) //로그인 성공 핸들러 등록
				.failureForwardUrl("/user/login-fail")
				.permitAll() //위에 명시한 모든 요청경로를 로그인 없이 요청할수 있도록 설정 
		)
		.logout(config ->
			config
				.logoutUrl("/user/logout")//Spring Security 가 자동으로 로그아웃 처리 해줄 경로 설정
				.logoutSuccessHandler((request, response, auth)->{
					Cookie cook=new Cookie(jwtName, null);
					//쿠키를 삭제하기 위해 setMaxAge(0)
					cook.setMaxAge(0);
					cook.setPath("/");
					response.addCookie(cook);
					//쿠키 삭제후에 최상위 경로로 리다일렉트 이동
					response.sendRedirect(request.getContextPath()+"/");
				})
				.permitAll()
		)
		.exceptionHandling(config ->
			//403 forbidden 인 경우(권한이 부족해) forward 이동 시킬 경로 설정 
			config.accessDeniedPage("/user/denied")
		)
		.sessionManagement(config -> 
			//세션을 사용하지 않도록 설정한다.
			config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		)
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		.requestCache(config->config.requestCache(cookCache));
		//설정 정보를 가지고 있는 HttpSecurity 객체의 build()메소드를 호출해서 리턴되는 객체를 리턴해준다
		return httpSecurity.build();
	}
	
	//비밀번호를 암호화 해주는 객체를 bean 으로 만든다.
	@Bean
	PasswordEncoder passwordEncoder() { 
		//여기서 리턴해주는 객체도 bean 으로 된다.
		return new BCryptPasswordEncoder();
	}
	//인증 메니저 객체를 bean 으로 만든다. (Spring Security 가 자동 로그인 처리할때도 사용되는 객체)
	@Bean //이 메소드에서 리턴하는 객체를 bean으로 만든다.
	AuthenticationManager authenticationManager(HttpSecurity http,
			BCryptPasswordEncoder encoder, UserDetailsService service) throws Exception{
		//적절한 설정을한 인증 메니저 객체를 리턴해주면 bean 이 되어서 Spring Security 가 사용한다 
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(service)
				.passwordEncoder(encoder)
				.and()
				.build();
	}
	
	//쿠키 캐시를 bean으로 만든다.
	@Bean
	CookieRequestCache getCookieRequestCache() {
		return new CookieRequestCache();
	}
}
