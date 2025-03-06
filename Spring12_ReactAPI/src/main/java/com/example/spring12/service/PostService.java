package com.example.spring12.service;

import java.util.List;

import com.example.spring12.dto.PostDto;
import com.example.spring12.dto.PostPageResponse;

public interface PostService {
	public PostDto save(PostDto dto);
	public List<PostDto> findAll();
	public PostPageResponse findPage(int pageNum);
	public PostDto delete(long id);
	public PostDto updateAll(PostDto dto);
	public PostDto update(PostDto dto);
	public PostDto find(long id);
}
