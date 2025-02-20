package com.example.spring06.service;

import java.util.List;

import com.example.spring06.dto.MemberDto;

public interface MemberService {
	public List<MemberDto> findAll();
	public MemberDto findById(int num);
	public void save(MemberDto dto);
	public void update(MemberDto dto);
	public void deleteById(int num);
	
}
