package com.example.spring10.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.spring10.dto.PostDto;

//bean으로 만들기 위한 어노테이션
@Repository
public class PostDaoImpl implements PostDao {

	//의존 객체 주입
	@Autowired private SqlSession session;
	
	@Override
	public List<PostDto> getList(PostDto dto) {

		return session.selectList("post.getList", dto);
	}

	@Override
	public int insert(PostDto dto) {
		
		return session.insert("post.insert", dto);
	}

	@Override
	public int delete(long num) {
		
		return session.delete("post.delete", num);
	}

	@Override
	public int update(PostDto dto) {
		
		return session.update("post.update", dto);
	}

	@Override
	public int getCount(PostDto dto) { //전체 글의 갯수
		//resultType은 int 이다.
		return session.selectOne("post.getCount", dto);
	}

}
