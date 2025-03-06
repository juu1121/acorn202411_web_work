package com.example.spring12.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PostPageResponse {
	private List<PostDto> list; //글목록
	private int startPageNum;
	private int endPageNum;
	private int totalPageCount;
	private int PageNum;
}
