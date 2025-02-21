package com.example.spring10.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.spring10.dto.UserDto;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SqlSession session;

	@Override
	public UserDto getData(long num) {
		/*
		 * namespace : user
		 * sql's id : getByNum
		 * resultType : UserDto
		 * parameterType : long
		 */
		return session.selectOne("user.getByNum", num);
	}

	@Override
	public UserDto getData(String userName) {
		/*
		 * parameterType : String
		 * resultType : UserDto
		 */
		return session.selectOne("user.getByUserName", userName);
	}

	@Override
	public int insert(UserDto dto) {

		return session.insert("user.insert", dto);
	}

	@Override
	public int updatePwd(UserDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(UserDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
