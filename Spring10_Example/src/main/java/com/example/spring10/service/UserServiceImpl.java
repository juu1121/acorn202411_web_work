package com.example.spring10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring10.dto.UserDto;
import com.example.spring10.exception.PasswordException;
import com.example.spring10.repository.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;
	
	//SecurityConfig클래스에 @Bean 설정으로 bean이 된 PasswordEncoder 객체 주입 받기
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserDto getByNum(long num) {
		return dao.getData(num);
	}

	@Override
	public UserDto getByUserName(String userName) {
		
		return dao.getData(userName);
	}

	@Override
	public void createUser(UserDto dto) {
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

	@Override
	public void updateUserInfo(UserDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(UserDto dto) {
		//1. 로그인 된 userName을 얻어낸다.
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		//2. 기본의 비밀번호를 DB에서 읽어와서 (암호화된 비밀번호)
		String encodedPwd = dao.getData(userName).getPassword();
		//3. 입력한(암호화되지않은 구 비밀번호)와 일치하는지 비교해서
		// .checkpw(암호화되지않은 비밀번호, 암호화된 비밀번호)
		boolean isValid = BCrypt.checkpw(dto.getPassword(), encodedPwd);
		//4. 만일 일치하지않으면 Exception을 발생시킨다
		if(!isValid) {
			throw new PasswordException("기존 비밀번호가 일치하지 않습니다.");
		}
		
		//5. 일치하면 새 비밀번호를 암호화해서 dto에 담은 다음
		dto.setNewPassword(encoder.encode(dto.getNewPassword()));
		//6. userName도 dto에 담고
		dto.setUserName(userName);
		//7. DB에 비밀번호 수정반영을 한다.
		dao.updatePassword(dto);
	}

}
