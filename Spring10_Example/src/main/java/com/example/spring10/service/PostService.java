package com.example.spring10.service;

import java.util.List;

import com.example.spring10.dto.PostDto;
import com.example.spring10.dto.PostListDto;

public interface PostService {
	public PostListDto getPosts(int pageNum, PostDto search);
	public long createPost(PostDto dto);
	public PostDto getByNum(long num); //글 하나의 정보를 가져오는!
	public PostDto getDetail(PostDto dto); //이전글의 글번호, 다음글의 글번호도 가져오겠다
	public void updatePost(PostDto dto);
	public void deletePost(long num);
	public void manageViewCount(long num, String sessionId);
	
	
	
}
