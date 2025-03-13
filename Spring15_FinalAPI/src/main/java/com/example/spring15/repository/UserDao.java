package com.example.spring15.repository;

import com.example.spring15.dto.UserDto;

public interface UserDao {
	//메소드는 오버로딩가능 = 타입만 다른, 같은명의 메소드!
	public UserDto getData(long num);
	public UserDto getData(String userName);
	public int insert(UserDto dto);
	public int updatePassword(UserDto dto);
	public int update(UserDto dto);
}
