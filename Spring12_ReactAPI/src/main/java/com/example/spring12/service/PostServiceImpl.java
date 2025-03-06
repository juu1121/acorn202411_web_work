package com.example.spring12.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring12.dto.PostDto;
import com.example.spring12.dto.PostPageResponse;
import com.example.spring12.entity.Post;
import com.example.spring12.repository.PostRepository;



@Service
public class PostServiceImpl implements PostService {
	//JpaRepository 객체를 주입 받는다.
	@Autowired PostRepository repo;
	
	//한 페이지에 몇개의 row를 출력할 것인지에 대한 값
	final int PAGE_ROW_COUNT=10;
	//페이징처리 UI에 페이지번호를 몇개씩 출력할지에 대한 값
	final int PAGE_DISPLAY_COUNT=5;
	
	@Override
	public PostDto save(PostDto dto) {
		// dto를 Entity로 변경해서 저장하고, 결과를 Entity로 리턴받는다.
		Post post = repo.save(Post.toEntity(dto));
		//결과 Entity를 dto로 변경해서 리턴한다.
		return PostDto.toDto(post);
	}

	@Override
	public List<PostDto> findAll() {
		//Entity List를 얻어내서
		List<Post> list = repo.findAll();
		//dto의 List로 변경해서 리턴한다.
		return list.stream().map(PostDto::toDto).toList();
	}

	@Override
	public PostDto delete(long id) {
		//삭제할 Entity를 미리 얻어낸다. 
		//case1) .orElseThrow()는 찾아서 없으면 예외를 발생하고, 있으면 Post가 리턴된다.
		//Post post = repo.findById(id).orElseThrow();
		
		//case2) 만일 원하는 예외를 발생시키고 싶으면, 람다함수를 사용해서 예외발생 => 리턴한 RuntimeException이 자동으로 throw된다
		//커스텀 예외 발생시키고싶다면, .orElseThrow()에 람다함수를 넣어서 "()-> nuw 예외객체 생성" 하면된다.
		Post post = repo.findById(id).orElseThrow(()->new RuntimeException("post not found!"));
		
		//삭제작업을 하고
		repo.deleteById(id);
		//삭제된 Entity를 dto로 변경해서 리턴한다.
		return PostDto.toDto(post);
	}

	@Override
	public PostDto updateAll(PostDto dto) {
		//Entity의 id를 제외한 모든 필드를 수정한다.
		Post post = repo.save(Post.toEntity(dto));
		//수정된 Entity를 dto로 변경해서 리턴하기
		return PostDto.toDto(post);
	}
	/*
	 * JPA에서 동일한 Transaction 내에서 특정 Entity를 find 한 다음
	 * 해당 Entity의 setter 메소드를 이용해서 특정 필드를 수정하면
	 * Transaction이 종료될때, Entity가 변경되었는지를 확인해서
	 * 자동으로 DB에 변경된 내용만 수정반영한다.
	 * => 엔티티가 변경되었는지 체크하는걸 더티체크라고 함 //트랜젝션이 종료될때 엔티티가 변경되었으면, 그걸 알아서 적용시킨다고
	 * but, 단일 트랜젝션으로 묶어야한다 = @Transactional  //트랜젝션이 실패하면 자동 롤백된다
	 */
	@Transactional //update() 메소드를 단일 Transactional으로 묶어준다. 
	@Override
	public PostDto update(PostDto dto) {
		// post id를 이용해서 수정할 Entity를 얻어온다.
		Post post = repo.findById(dto.getId()).orElseThrow();
		
		//title이 null이 아닐때만 title 수정
		if(dto.getTitle() != null) {
			post.setTitle(dto.getTitle());
		}
		
		//위의코드를 한줄코딩으로 작성 => ofNullable에 리턴되는값이 null일수도있지만,  ifPresent 만약에 존재한다면 post엔티티의 setTitle메소드를 호출하면서 dto.getTitle값을 넣어준다
		//Optional.ofNullable(dto.getTitle()).ifPresent(post::setTitle);
		
		//author가 null이 아닐때만 author수정
		if(dto.getAuthor() != null) {
			post.setAuthor(dto.getAuthor());
		}
		
		//위와 같은 동작
		//Optional.ofNullable(dto.getAuthor()).ifPresent(post::setAuthor);
		
		return PostDto.toDto(post);
	}

	@Override
	public PostDto find(long id) {
		//post를 찾고 없으면 예외 발생시키기
		Post post = repo.findById(id).orElseThrow();
		
		//찾은 Entity를 dto로 변경해서 리턴하기
		return PostDto.toDto(post);
	}

	@Override
	public PostPageResponse findPage(int pageNum) {
		// 한 페이지에 몇개씩 표시할것인지 //위에서 상수값으로 정의함
		
		//id칼럼에 대해서 내림차순으로 정렬하라는 정보를 가지고있는 Sort객체 만들기
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		//PageNum과 PAGE_ROW_COUNT와 Sort객체를 전달해서 Pageable객체를 얻어낸다.
		Pageable pageable = PageRequest.of(pageNum-1, PAGE_ROW_COUNT, sort);
		//Pageable객체를 전달해서 해당 페이지 정보를 얻어온다음
		//한페이지에 몇개씩 보여줄지 뭐 알려주는 정보는(해당 페이지 정보) pageable에 들어있고, 그 정보에 맞는 post만 select해온다!
		Page<Post> page = repo.findAll(pageable);
		
		//글목록
		List<PostDto> list = page.stream().map(PostDto::toDto).toList();
		
		
		
		//하단 시작 페이지 번호 
		int startPageNum = 1 + ((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//하단 끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//전체 페이지의 갯수 구하기 (Page객체에 이미 전체페이지의 갯수가 계산되어들어있다,so db에서 row갯수를 계산할필요없다)
		int totalPageCount=page.getTotalPages();
		//끝 페이지 번호가 이미 전체 페이지 갯수보다 크게 계산되었다면 잘못된 값이다.
		if(endPageNum > totalPageCount){
			endPageNum=totalPageCount; //보정해 준다. 
		}
		
		
		
		//위의 정보를 이용해서 PostPageResponse객체에 담아서 리턴한다.
		return PostPageResponse.builder()
				.list(list)
				.startPageNum(startPageNum)
				.endPageNum(endPageNum)
				.totalPageCount(totalPageCount)
				.PageNum(pageNum)
				.build();
	}

}
