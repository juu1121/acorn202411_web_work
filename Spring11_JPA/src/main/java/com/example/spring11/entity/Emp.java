package com.example.spring11.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
 
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Emp {
	@Id
	private Integer empno;
	private String ename;
	private String job;
	private Integer mgr;
	private Date hiredate;
	private Double sal;
	private Double comm;
	//부서번호는 하나만 있어도 되기때문에 주석 처리한다.
	//private Integer deptno; 
	/*
	 * Emp 객체 하나는 사원 한명의 정보를 가지고있다
	 * Dept 객체 하나는 부서 하나의 정보를 가지고있다.
	 * Emp객체 안에 있는 Dept객체는 Emp 객체가 가지고있는 해당사원의 부서정보를 가지게 하고싶다.
	 * => dept필드를 추가해서, 그 사원이 근무하는 부서의 정보를 담는다면 => emp엔티티객체로 사원정보와, 부서정보를 모두 담을수있는 Dto의 값을 넣어줄수있다..
	 * 
	 * @JoinColumn(name="EMP테이블의 칼럼명", referencedColumnName = "dept테이블의 어떤 칼럼을 참조할지 결정한다/ 생략시 자동으로 @Id칼럼 참조")
	 */
	@ManyToOne
	@JoinColumn(name="deptno", referencedColumnName = "deptno")
	private Dept dept;
}



















