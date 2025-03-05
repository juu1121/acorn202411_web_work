package com.example.spring12.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring12.entity.Post;

/*
 * JpaRepository<Entity클래스type, id역할을 하는 칼럼의 type>
 * 위와 같이 설정만 하면 PostRepository의 구현 클래스가 자동으로 만들어지고 해당 클래스로 생성된 객체가 bean으로 관리된다.
 */
public interface PostRepository extends JpaRepository<Post, Long>{

}
