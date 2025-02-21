package com.example.spring10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring10.dto.UserDto;
import com.example.spring10.repository.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;
	
	//SecurityConfig클래스에 @Bean 설정으로 bean이 된 PasswordEncoder 객체 주입 받기
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserDto findByNum(long num) {
		return dao.getData(num);
	}

	@Override
	public UserDto findByUserName(String userName) {
		
		return dao.getData(userName);
	}

	@Override
	public void save(UserDto dto) {
		//저장할때 비밀번호를 암호화 한 후에 저장되도록 한다. //1. 전달받은 dto에는 내가 입력한 비번이있음
		String encodedPwd = encoder.encode(dto.getPassword()); //2. 그 비번을 암호화하고
		//인코딩 된 비밀번호를 다시 dto객체에 넣어주고 //3. 입력해서 암호화 된 비밀번호를 dto객체에 다시 넣어줌
		dto.setPassword(encodedPwd);
		//DB에 저장한다. //4. so, 입력한 dto가 db에 저장되므로, 내가 입력한 비번이 저장되는것!
		int rowCount = dao.insert(dto);
		if(rowCount == 0) {
			throw new RuntimeException("회워정보 저장 실패!");
		}
		
	}

}
