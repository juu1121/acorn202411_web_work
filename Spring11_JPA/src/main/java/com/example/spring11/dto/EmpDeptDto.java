package com.example.spring11.dto;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.example.spring11.entity.Emp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmpDeptDto {
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private String hiredate;
	private double sal;
	private double comm;
	//Emp와 Dept에 같이 있는 정보
	private int deptno;
	//Dept Entity에만 있는 정보
	private String dname;
	private String loc;
	
	// Entity 를 dto 로 변경해서 리턴하는 static 메소드
	public static EmpDeptDto toDto(Emp emp) {
		// Date type 을 이용해서 원하는 문자열 형식을 얻어내기 위한 객체
		SimpleDateFormat sdf=
				new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
		//객체를 이용해서 원하는 문자열을 얻어낸다 
		// 2014.08.01 목 오후 6:10:30  이런 형식의 문자열 
		String hiredate = sdf.format(emp.getHiredate());
				
		return EmpDeptDto.builder()
				.empno(emp.getEmpno())
				.ename(emp.getEname())
				.job(emp.getJob())
				.mgr(emp.getMgr()==null ? 0 : emp.getMgr()) //int x Integer
				.hiredate(hiredate)
				.sal(emp.getSal())
				.comm(emp.getComm()==null ? 0 : emp.getComm()) //double x Double
				.deptno(emp.getDept().getDeptno())
				.dname(emp.getDept().getDname())
				.loc(emp.getDept().getLoc())
				.build();
	}
}


