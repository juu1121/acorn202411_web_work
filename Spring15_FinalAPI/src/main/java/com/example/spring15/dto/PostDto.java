package com.example.spring15.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Alias("postDto") //mapper xml에서 PostDto type을 편하게 선언하기 위해
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDto {
	private long num; //글번호 long타입...int로 하면 글이 20억개를 못 넘어
	private String writer;
	private String title;
	private String content;
	private int viewCount;
	private String createdAt;
	private String updatedAt;
	//페이징 처리할때 필요한 필드
	private int startRowNum;
	private int endRowNum;
	private String condition; //검색조건 writer 또는 title 또는 title+content
	private String keyword;	//검색 키워드
	private long prevNum;	//이전글의 글번호
	private long nextNum;	//다음글의 글번호
	

	
}
