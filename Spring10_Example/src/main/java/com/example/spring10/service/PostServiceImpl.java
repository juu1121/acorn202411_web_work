package com.example.spring10.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.spring10.dto.PostDto;
import com.example.spring10.dto.PostListDto;
import com.example.spring10.repository.PostDao;

@Service
public class PostServiceImpl implements PostService {

	//한 페이지에 몇개씩 표시할 것인지
	final int PAGE_ROW_COUNT=5;
	//하단 페이지를 몇개씩 표시할 것인지
	final int PAGE_DISPLAY_COUNT=5;
	
	@Autowired private PostDao postDao;
	/*
	 * pageNum과 검색조건, 키워드가 담겨 있을 수 있는 PostDto를 전달하면
	 * 해당 글 목록을 리턴하는 메소드 (없으면null로 전달~)
	 */
	@Override
	public PostListDto getPosts(int pageNum, PostDto search) { //pageNum과 검식키워드가 있을수도있고없을수도있는 dto를 이용해서!
		
		//보여줄 페이지의 시작 ROWNUM
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지의 끝 ROWNUM
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		//하단 시작 페이지 번호 
		int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//하단 끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//전체 글의 갯수
		int totalRow=postDao.getCount(search);
		//전체 페이지의 갯수 구하기
		int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
		if(endPageNum > totalPageCount){
			endPageNum=totalPageCount; //보정해 준다. 
		}	
		
		// startRowNum 과 endRowNum 을 PostDto 객체에 담아서
		search.setStartRowNum(startRowNum);
		search.setEndRowNum(endRowNum);
		
		//글 목록 얻어오기
		List<PostDto> list = postDao.getList(search);
		
		String findQuery="";
		if(search.getKeyword() != null) {
			findQuery="&keyword="+search.getKeyword()+"&condition="+search.getCondition();
		}
		
		PostListDto dto = PostListDto.builder()
				.list(list)
				.startPageNum(startPageNum)
				.endPageNum(endPageNum)
				.totalPageCount(totalPageCount)
				.pageNum(pageNum)
				.totalRow(totalRow)
				.findQuery(findQuery)
				.condition(search.getCondition())
				.keyword(search.getKeyword())
				.build();
		
		return dto;
	}

	@Override
	public long createPost(PostDto dto) {
		
		//글 작성자를 얻어내어 dto에 담는다.//글작성자는 로그인된 user
		String writer = SecurityContextHolder.getContext().getAuthentication().getName();
		dto.setWriter(writer);		
		
		//저장할 글번호를 미리 얻어온다.
		long num = postDao.getSequence();
		//dto에 글번호를 넣은 다음 DB에 저장한다.
		dto.setNum(num);
		postDao.insert(dto);
		//글번호를 리턴해준다.
		return num;
	}

	@Override
	public PostDto getByNum(long num) {

		return postDao.getData(num);
	}

	@Override
	public PostDto getDetail(PostDto dto) {
		
		return postDao.getDetail(dto);
	}

	@Override
	public void updatePost(PostDto dto) {
		
		postDao.update(dto);
	}

	@Override
	public void deletePost(long num) {
		postDao.delete(num);
		
	}

	@Override
	public void manageViewCount(long num, String sessionId) {
		//이미 읽었는지 여부를 얻어낸다.
		boolean isReaded=postDao.isReaded(num, sessionId);
		if(!isReaded) {
			//글조회수도 1증가시킨다.
			postDao.addViewCount(num);
			//이미 읽었다고 표시한다.
			postDao.insertReaded(num, sessionId);
		}
		
	}

}
