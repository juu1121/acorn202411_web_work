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
		//1. Member Entity의 목록
		//List<Member> entityList = repo.findAll();
		
		//2. 추가한 메소드를 이용해서 num에 대해서 내림차순 정렬된 목록을 얻어낼수있다.
		//List<Member> entityList = repo.findAllByOrderByNumDesc();
		
		//3.
		List<Member> entityList = repo.getList();
		
		
/*		
		//MemberDto의 목록으로 만들어서 리턴해야한다.
		List<MemberDto> list = new ArrayList<>();
		//반복문돌면서 member객체를 순서대로 참조
		for(Member tmp:entityList) {
			list.add(MemberDto.toDto(tmp));
		}
*/
		//stream()을 이용하면 한줄의 coding으로 위의 동작을 할수있다.
//		List<MemberDto> list = entityList.stream().map(item->MemberDto.toDto(item)).toList();
		List<MemberDto> list = entityList.stream().map(MemberDto::toDto).toList();
		
		return list;
	}

	@Override
	public void saveMember(MemberDto dto) {
		//dto를 entity로 변경해서 저장한다.
		repo.save(Member.toEntity(dto)); 
		
	}

	@Override
	public void deleteMember(int num) {
		repo.deleteById(num);
		
	}

	@Override
	public MemberDto getMember(int num) {
		//id를 이용해서 Member Entity type을 얻어낸다.
		//옵셔널에서 엔티티를 얻어내는 방법 = get메소드
		Member member = repo.findById(num).get();
		
		// Emtity를 dto로 변경해서 리턴한다.
		return MemberDto.toDto(member);
	}

	@Override
	public void updateMember(MemberDto dto) {
		// MemberDto를 Entity로 변경해서 save(수정)한다. 
		repo.save(Member.toEntity(dto));
		
	}

}
















