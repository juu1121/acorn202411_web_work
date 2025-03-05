package com.example.spring12.entity;

import com.example.spring12.dto.PostDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * http://localhost:9000/h2-console로 접속하면 만들어진 db테이블을 확인할수있다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //참조데이터타입
	private String title;
	private String author;
	
	//dto를 엔티티로 변환해서 리턴하는 static 메소드 만들기
	public static Post toEntity(PostDto dto) {
		/*
		 * Entity로 변환할때 dto에 숫자가 0인경우에 null을 넣어주어야한다.
		 * Why? 
		 * -JpaRepository의 .save()메소드는 insert와 update가 겸용
		 * @id칼럼이 null이면, insert를 시도하고
		 * @id칼럼이 null이 아니면, update를 시도한다.
		 * ==> 0이 들어가면 update를 시도한다!
		 * 
		 */
		return Post.builder()
				.id(dto.getId() == 0 ? null : dto.getId())
				.title(dto.getTitle())
				.author(dto.getAuthor())
				.build();
	}
}
