package com.example.spring12.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI  customOpenAPI() {
		Info info=new Info()
				.title("Post API 문서입니다")
				.version("1.0")
				.description("어쩌구... 저쩌구...");
		
		return new OpenAPI().info(info);
	}
}