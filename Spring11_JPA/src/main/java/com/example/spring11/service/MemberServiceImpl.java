package com.example.spring11.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring11.dto.MemberDto;
import com.example.spring11.entity.Member;
import com.example.spring11.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired private MemberRepository repo; //JPA가 관리하는 데이터의 저장소라고 생각
	
	@Override
	public List<MemberDto> getAll() {
		//Member Entity의 목록
		List<Member> entityList = repo.findAll();
/*		
		//MemberDto의 목록으로 만들어서 리턴해야한다.
		List<MemberDto> list = new ArrayList<>();
		//반복문돌면서 member객체를 순서대로 참조
		for(Member tmp:entityList) {
			list.add(MemberDto.toDto(tmp));
		}
*/
		
//		List<MemberDto> list = entityList.stream().map(item->MemberDto.toDto(item)).toList();
		List<MemberDto> list = entityList.stream().map(MemberDto::toDto).toList();
		
		return list;
	}

	@Override
	public void saveMember(MemberDto dto) {
		//dto를 entity로 변경해서 저장한다.
		repo.save(Member.toEntity(dto)); 
		
	}

}
