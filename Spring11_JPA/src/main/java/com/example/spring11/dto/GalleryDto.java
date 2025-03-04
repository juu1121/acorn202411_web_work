package com.example.spring11.dto;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.example.spring11.entity.Gallery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GalleryDto {
	private long id;
	private String writer;
	private String title;
	//Dto의 날짜는 보통 String type으로 선언한다.
	private String createdAt;
	
	//Entity를 dto로 만들어서 리턴하는 static메소드
	public static GalleryDto toDto(Gallery gallery) {
		//gallery.getCreatedAt() => Date Type이 리턴되는데, 
		//이 타입을 이용해서 원하는 타입으로 변경!
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd E a hh:mm:ss", Locale.KOREA);
		//2025.03.04 화 오후3:10:25 형식의 문자열을 얻어낼수있는 객체이다.
		String result = sdf.format(gallery.getCreatedAt());
		System.out.println(result);
		
		return GalleryDto.builder()
				.id(gallery.getId())
				.writer(gallery.getWriter())
				.title(gallery.getTitle())
				.createdAt(result)
				.build();
	}
}
