package com.example.spring03.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class MemberDto {
	private int num;
	private String name;
	private String addr;
}
