package com.example.spring08;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Spring08JavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring08JavaApplication.class, args);
		//of() 메소드로 만든 List는 읽기전용(Read Only)이다._사이즈변경도 불가능
		List<String> names=List.of("김구라", "해골", "원숭이");
		//names.add("주뎅이"); //동작하지않는다 (예외 발생)
		
		//List의 stream() 메소드를 호출하면 Stream type이 리턴된다.
		Stream<String> stream=names.stream();
		
		Function<String, String> f = (item) -> {
			return item+"님";
		};
		//위의 Function type을 줄여서 쓰면 아래와 같다.
		Function<String, String> f2= item-> item+"놈";
		
		List<String> names2=stream.map(f).toList();
		System.out.println("names2 : " + names2);
		
		List<String> names3=names.stream().map(f2).toList();
		System.out.println("names3 : " + names3);
		
		List<String> names4=names.stream().map(item->item+"츄").toList();
		System.out.println("names4 : " + names4);
		
		List<Integer> nums = List.of(-10, 20, 30, -5, 25, -30);
		
		//nums에서 양의 정수만 남겨진 새로운 List얻어내기
		List<Integer> newNums = nums.stream().filter( item -> item>0).toList();
		System.out.println("newNums : " + newNums);
		
		//nums에서 양의 정수만 남기고 2배를 곱한 새로운 List얻어내기
		List<Integer> newNums2 = nums.stream().filter( item -> item>0).map(item -> item*2).toList(); 
		System.out.println("newNums2 : " + newNums2);
		
		//nums에서 양의 정수만 남기고 2배를 곱한 새로운 List얻어내서 순서대로 해당 숫자를 콘솔창에 모두 출력하기
		nums.stream().filter(item->item>0).map(item->item*2).forEach(item->{
			System.out.println(item);
		});
		
		//문자열(String)이 들어있는 List
		List<String> strNums = List.of("10", "20", "30", "40", "50");
		
		//위의 List를 활용해서 List<Integer>를 얻어내보세요
		List<Integer> intNums = strNums.stream().map(item->Integer.parseInt(item)).toList();
		System.out.println(intNums);
		
		//Integer::parseInt  ->Integer클래스가 가지고있는  parseInt메소드를 참조해서 map()함수에 전달해서 동일한작업가능
		List<Integer> intNums2 = strNums.stream().map(Integer::parseInt).toList();
		System.out.println(intNums2);		
	
	}

}
