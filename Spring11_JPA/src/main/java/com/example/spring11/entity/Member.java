package com.example.spring11.entity;

import com.example.spring11.dto.MemberDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name="MEMBER_INFO")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer num;
	private String name;
	private String addr;
	
	//dto를 entity로 변환하는 static메소드  //memberdto를 integer로 쓰면, 삼항연산자 노필요 
	public static Member toEntity(MemberDto dto) {
		return Member.builder()
				.num(dto.getNum() ==0 ? null : dto.getNum())
				.name(dto.getName())
				.addr(dto.getAddr())
				.build();
	}
	
	
}







