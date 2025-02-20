package com.example.spring06.dto;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
/*
 * type에 별칭을 부여하고 Mapper xml문서에서 별칭을 이용해서 parameterType과 resultType 설정을 할 수 있다.
 * 
 */
@Alias("memberDto") //소문자로시작하는 memberDto라고 별칭을 붙임!
@Setter
@Getter
public class MemberDto {
	private int num;
	private String name;
	private String addr;
}
