package com.example.spring16.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import reactor.core.publisher.Mono;

@Service
public class GeminiService2 {
    private final WebClient webClient;
    private final Gson gson = new Gson();
    private final String apiKey;
    private final String url;
    
    //생성자에 필요한 data 3 개 전달받기 
    public GeminiService2(WebClient.Builder builder, 
    		@Value("${gemini.key}") String apiKey,
    		@Value("${gemini.url}") String url  //서비스 객체가 생성되는 시점에 @Value 가 읽어와 지지 않는다.
    ) {
    	//전달 받은 내용을 필드에 저장하기 
	   this.apiKey=apiKey;
	   this.url=url;
	   this.webClient=builder.baseUrl(url).build();
    }

    public Mono<String> getFoodCategory(String food) {
        String str = """
            클라이언트가 입력한 음식: "%s"
            
            해당 음식의 카테고리를 JSON 형식으로 반환해.
            응답은 아래 형식을 따라야 해:
            { "category": "한식" }
            
            ["한식", "중식", "일식", "양식", "기타"] 중 하나만 "category" 값으로 넣어줘.
            설명 없이 JSON 객체만 반환해.
            markdown 형식으로 응답하면 안되.
            맨뒤에 서비스2임를 붙여
        """.formatted(food);

        return getChatResponse(str);
    }
    
    //얘는 Mono가 아닌 스트링리턴..//동기동작하게 하려나봥
    public String getChatResponseSync(String prompt) {
    	//GeminiRequest 구성하기 
        GeminiRequest request = new GeminiRequest();
        GeminiRequest.Content content = new GeminiRequest.Content();
        GeminiRequest.Part part = new GeminiRequest.Part();
        //part에 질문을 담는다.
        part.setText(prompt);
        content.setParts(List.of(part));
        request.setContents(List.of(content));

        String result = webClient.post()
                .uri(uriBuilder -> uriBuilder.path(":generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request) // Map 객체 대신에 GeminiRequest 객체를 넣어주면 json으로 변경된다.
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(responseBody -> System.out.println(responseBody))
                .flatMap(responseBody -> {
                    try {
                        return Mono.just(extractResponse(responseBody));
                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("JSON 파싱 오류", e));
                    }
                }).block(); // block 메소드를 호출하면 결과문자열을 받아올때까지 기다린다.
        return result;
    }    

    public Mono<String> getChatResponse(String prompt) {
    	//GeminiRequest 구성하기 
        GeminiRequest request = new GeminiRequest();
        GeminiRequest.Content content = new GeminiRequest.Content();
        GeminiRequest.Part part = new GeminiRequest.Part();
        //part에 질문을 담는다.
        part.setText(prompt);
        content.setParts(List.of(part));
        request.setContents(List.of(content));

        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(":generateContent")
                        .queryParam("key", apiKey)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request) // Map 객체 대신에 GeminiRequest 객체를 넣어주면 json으로 변경된다.
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(responseBody -> System.out.println(responseBody))
                .flatMap(responseBody -> {
                    try {
                        return Mono.just(extractResponse(responseBody));
                    } catch (Exception e) {
                        return Mono.error(new RuntimeException("JSON 파싱 오류", e));
                    }
                });
    }
    //응답된 json을 파싱해서 문자열 얻어내는 메소드
    //if문 사용하지않고 Optional을 사용해서 null이면 응답없음리턴하게함
    private String extractResponse(String responseJson) {
        try {
            GeminiResponse geminiResponse = gson.fromJson(responseJson, GeminiResponse.class);

            return Optional.ofNullable(geminiResponse)
                    .map(GeminiResponse::getCandidates)
                    .filter(candidates -> !candidates.isEmpty())
                    .map(candidates -> candidates.get(0))
                    .map(GeminiResponse.Candidate::getContent)
                    .map(GeminiResponse.Content::getParts)
                    .filter(parts -> !parts.isEmpty())
                    .map(parts -> parts.stream().map(GeminiResponse.Part::getText).collect(Collectors.joining("\n")))
                    .orElse("응답 없음");

        } catch (JsonSyntaxException e) {
            return "JSON 파싱 오류: " + e.getMessage();
        }
    }
}