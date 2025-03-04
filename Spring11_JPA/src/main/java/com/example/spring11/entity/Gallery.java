package com.example.spring11.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity //이름을 따로 지정하지않으면 만들어지는 테이블은 class명과 동일
public class Gallery {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String writer;
	private String title;
	private Date createdAt;
	
	//Entity를 영속화(insert) 하기 직전에 뭔가 작업할게있으면 @PrePersist 어노테이션을 활용하면된다.
	// @PreUpdate는 수정(update)하기 직전에 호출된다.
	@PrePersist
	public void onPersist() {
		//오라클에서 데이터를 넣을때 SYSDATE 함수를 이용해서 넣는 효과를 낸다.
		createdAt = new Date();
	}
}
