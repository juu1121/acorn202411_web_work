package com.example.spring10.service;

import java.util.List;

import com.example.spring10.dto.PostDto;
import com.example.spring10.dto.PostListDto;

public interface PostService {
	public PostListDto getPosts(int pageNum, PostDto search);
	public void createPost(PostDto dto);
	
	
	
}
