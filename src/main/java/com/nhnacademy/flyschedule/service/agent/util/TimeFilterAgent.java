package com.nhnacademy.flyschedule.service.agent.util;

import com.nhnacademy.flyschedule.dto.Flight.FlightInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TimeFilterAgent {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    public LocalTime parseTime(String timeInput){
        if(timeInput == null || timeInput.isBlank()){
            throw new IllegalArgumentException("시간을 입력해주세요");
        }


        String normalized = timeInput.trim().toLowerCase();

        if(normalized.contains("오후")){
            String numbersOnly = normalized.replaceAll("[^0-9]", "");
            if(!numbersOnly.isEmpty()){
                int hour = Integer.parseInt(numbersOnly);
                if(hour < 12) hour +=12;
                if(hour >24 || hour <0){
                    throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
                }
                return LocalTime.of(hour,0);
            }
        }

        if(normalized.contains("오전")){
            String numbersOnly = normalized.replaceAll("[^0-9]", "");
            if(!numbersOnly.isEmpty()){
                int hour = Integer.parseInt(numbersOnly);
                if(hour < 0 || hour >= 12){
                    throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
                }
                if(hour == 12){
                    hour = 0;
                }
                return LocalTime.of(hour, 0);
            }
        }

        String cleaned = normalized.replace(":", "");
        return LocalTime.parse(cleaned, TIME_FORMATTER);
    }

    public List<FlightInfoResponse> filterAfterTime(List<FlightInfoResponse> flightInfoResponseList, LocalTime afterTime){
        if (flightInfoResponseList == null || flightInfoResponseList.isEmpty()) {
            return List.of();
        }

        return flightInfoResponseList.stream()
                .filter(flightInfoResponse -> {
                    try{
                        String timePart = flightInfoResponse.getDepPlandTime().substring(8,12);//출발 시간 추출
                        LocalTime departureTime = LocalTime.parse(timePart, TIME_FORMATTER);
                        return !departureTime.isBefore(afterTime);
                    }catch(Exception e){
                       log.warn("시간 파싱 실패: {}", flightInfoResponse.getDepPlandTime());
                       return false;
                    }
                }).collect(Collectors.toList());
    }
}
