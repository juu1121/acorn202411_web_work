package com.example.spring10.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.spring10.dto.FileDto;

@Repository
public class FileDaoImpl implements FileDao {

	@Autowired private SqlSession session;

	@Override
	public List<FileDto> getList() {
		
		return session.selectList("file.getList");
	}

	@Override
	public int insert(FileDto dto) {
		
		return session.insert("file.insert", dto);
	}

	@Override
	public FileDto getData(long num) {
		
		return session.selectOne("file.getData", num);
	}




}
