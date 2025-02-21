package com.example.spring10.service;

import com.example.spring10.dto.UserDto;

public interface UserService {
	public UserDto findByNum(long num);
	public UserDto findByUserName(String userName);
	public void save(UserDto dto); //회원가입할떄쓸거임
}
