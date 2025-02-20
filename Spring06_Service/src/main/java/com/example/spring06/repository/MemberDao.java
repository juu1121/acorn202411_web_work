package com.example.spring06.repository;

import java.util.List;

import com.example.spring06.dto.MemberDto;

public interface MemberDao {
	public List<MemberDto> getList();
	public void insert(MemberDto dto);
	//updat, delete는 수정, 삭제된 row의 갯수를 리턴하는모양으로 Dao메소드를 정의한다!
	public int update(MemberDto dto);
	public int delete(int num);
	public MemberDto getData(int num);
	
}
