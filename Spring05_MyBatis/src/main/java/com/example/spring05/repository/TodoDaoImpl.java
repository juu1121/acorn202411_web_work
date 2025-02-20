package com.example.spring05.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.spring05.dto.MemberDto;
import com.example.spring05.dto.TodoDto;

@Repository //dao를 bean으로 만들기
public class TodoDaoImpl implements TodoDao {

	@Autowired //DB관련작업위해 sqlsession주입
	private SqlSession session; 
	
	@Override
	public List<TodoDto> getList() {
		
		List<TodoDto> list = session.selectList("todo.getList");
		return list;
	}

	@Override
	public void insert(TodoDto dto) {
		
		session.insert("todo.insert", dto);
	}

	@Override
	public void update(TodoDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TodoDto getData(int id) {
		TodoDto dto = session.selectOne("todo.getData", id);
		return dto;
	}

}
