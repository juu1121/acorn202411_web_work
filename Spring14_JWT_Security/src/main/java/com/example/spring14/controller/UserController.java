package com.example.spring14.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.spring14.dto.UserDto;
import com.example.spring14.util.JwtUtil;

@Controller
public class UserController {
	
	@Autowired JwtUtil jwtUtil;
	//SecurityConfig 클래스에서 Bean이 된 AuthenticationManager 객체 주입받기
	@Autowired AuthenticationManager authManager; //이걸이용해 userName과 password가 맞는지 검증
	
	//@PreAuthorize("hasRole('ADMIN')") //ROLE를 따질때
	@Secured("ROLE_ADMIN") //authority 어소리지를 따질때
	@GetMapping("/secured/ping")
	@ResponseBody
	public String securedPing() {
		
		return "pong! pong!";
	}
	
	@GetMapping("/api/ping") //white list에 등록 되지 않은 요청은 token이 있어야 요청가능하다.
	@ResponseBody
	public String ping() {
		return "pong";
	}
	
	//리액트에서 로그인할때, 포스트맨으로 로그인할때, 이 메소드를 사용 //토큰없이요청해야해서 whitelist
	@PostMapping ("/api/auth") 
	@ResponseBody //ResponseEntity에는 @ResponseBody 기능이있어서 생략가능(자체기능) //만약 json응답하고싶으면 엔티티의 제너릭을dto로 하면 되겠징
	public ResponseEntity<String> auth(@RequestBody UserDto dto) throws Exception{
		Authentication authentication = null;
		
		try {
			UsernamePasswordAuthenticationToken authToken=
					new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword());
			//인증 매니저 객체를 이용해서 인증을 진행한다.
			authentication = authManager.authenticate(authToken);
		}catch(Exception e) {
			//예외가 발생하면 인증실패(아이디 혹은 비밀번호 틀림 등등...)
			e.printStackTrace();
			//401 UNAUTHORIZED 에러를 응답하면서 문자열 한 줄 보내기 //ResponseEntity를 이용해 HTTP 상태 코드 401 (Unauthorized) 와 함께 메시지를 반환
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 혹은 비번이 틀려여!");
		}

		//인증이 성공했다면, Authentication 객체에는 인증된 사용자 정보가 들어있다. userName, role등등
		//현재는 role을 하나만 부여하기 떄문에 0번방에 있는 데이터만 불러오면 된다.
		GrantedAuthority authority = authentication.getAuthorities().stream().toList().get(0);
		//ROLE_XXX형식
		String role = authority.getAuthority();
		//"role" 이라는 키값으로 Map에 담기
		Map<String, Object> claims = Map.of("role", role);		
		
		//예외가 발생하지 않고 여기까지 실행된다면 인증을 통과한것이다. 토큰을발급해서 응답한다.
		String token = jwtUtil.generateToken(dto.getUserName(), claims);
		//발급받은 토큰 문자열을 ResponseEntity에 담아서 리턴한다. 
		return ResponseEntity.ok("Bearer "+token);  //ok는 엔티티에서 정상응답 200으로 응답하는것!
	}
	
	//세션 허용갯수 초과시 
	@GetMapping("/user/expired")
	public String userExpired() {
		return "user/expired";
	}	
	
	//권한 부족시 or 403 인 경우 
	@RequestMapping("/user/denied")  //GET, POST 등 모두 가능
	public String userDenied() {
		return "user/denied";
	}
	
	//ROLL_STAFF , ROLL_ADMIN 만 요청 가능
	@GetMapping("/staff/user/list")
	public String userList() {
		
		return "user/list";
	}
	//ROLL_ADMIN 만 요청 가능
	@GetMapping("/admin/user/manage")
	public String userManage() {
		
		return "user/manage";
	}
	
	//@GetMapping("/user/loginform") + @PostMapping("/user/loginform")
	@RequestMapping("/user/loginform")
	public String loginform() {
		// templates/user/loginform.html 페이지로 forward 이동해서 응답 
		return "user/loginform";
	}
	
	//로그인이 필요한 요청경로를 로그인 하지 않은 상태로 요청하면 리다일렉트 되는 요청경로 
	@GetMapping("/user/required-loginform")
	public String required_loginform() {
		return "user/required-loginform";
	}
	// POST 방식 /user/login 요청후 로그인 성공인경우 forward 이동될 url 
	@PostMapping("/user/login-success")
	public String loginSuccess() {
		return "user/login-success";
	}
	
	//로그인 폼을 제출(post) 한 로그인 프로세즈 중에 forward 되는 경로이기 때문에 @PostMapping 임에 주의!
	@PostMapping("/user/login-fail")
	public String loginFail() {
		//로그인 실패임을 알릴 페이지
		return "user/login-fail";
	}	
}
