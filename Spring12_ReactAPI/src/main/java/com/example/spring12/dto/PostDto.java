package com.example.spring12.dto;

import com.example.spring12.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PostDto {
	private long id;
	private String title;
	private String author;
	
	// Entity를 dto로 변경해서 리턴해주는 static 메소드
	public static PostDto toDto(Post post) {
		
		return PostDto.builder()
				.id(post.getId())
				.title(post.getTitle())
				.author(post.getAuthor())
				.build();
	}
}
