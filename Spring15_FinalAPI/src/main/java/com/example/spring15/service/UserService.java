package com.example.spring15.service;

import com.example.spring15.dto.UserDto;

public interface UserService {
	public UserDto getByNum(long num);
	public UserDto getByUserName(String userName);
	public void createUser(UserDto dto); //회원가입할떄쓸거임
	public void updateUserInfo(UserDto dto);
	public void changePassword(UserDto dto);
}
