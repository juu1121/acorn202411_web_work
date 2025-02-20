package com.example.spring05.repository;

import java.util.List;

import com.example.spring05.dto.MemberDto;

public interface MemberDao {
	public List<MemberDto> getList();
	public void insert(MemberDto dto);
	public void update(MemberDto dto);
	public void delete(int num);
	public MemberDto getData(int num);
	
}
