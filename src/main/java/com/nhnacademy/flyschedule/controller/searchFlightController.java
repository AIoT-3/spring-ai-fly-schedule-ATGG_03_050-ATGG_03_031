package com.nhnacademy.flyschedule.controller;

import com.nhnacademy.flyschedule.dto.Flight.FlightInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search/flight")
@RequiredArgsConstructor
public class searchFlightController {

    private final ChatClient.Builder ollamaChatClientBuilder;
    @GetMapping("/ollama")
    public String searchFlight(@RequestParam String question){
       String response = ollamaChatClientBuilder.build().prompt()
               .system("너는 항공편 검색 도우미야. 친절하게 답변해줘. 사용자가 주는 값 외에 항공편을 필터링할 가격대나 출발 시간대를 스스로 판단하지마")
               .user(question)
               .call()
               .content();
       return response;
    }
}
