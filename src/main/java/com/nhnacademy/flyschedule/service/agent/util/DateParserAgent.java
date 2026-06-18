package com.nhnacademy.flyschedule.service.agent.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class DateParserAgent {
    private static final DateTimeFormatter API_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String parseDate(String dateInput){
        log.info("DateParserAgent: parseDate 호출");

        if(dateInput == null || dateInput.isBlank()){
            return LocalDate.now().format(API_DATE_FORMATTER);
        }
        String normalized = dateInput.trim();

        if(normalized.matches("\\d{8}")){
            return normalized;
        }
//        normalized = normalized.toLowerCase(); -> ?

        LocalDate today = LocalDate.now();

        return switch (normalized) {
            case "오늘" -> today.format(API_DATE_FORMATTER);
            case "내일" -> today.plusDays(1).format(API_DATE_FORMATTER);
            case "모레", "내일모레" -> today.plusDays(2).format(API_DATE_FORMATTER);
            case "글피" -> today.plusDays(3).format(API_DATE_FORMATTER);
            default -> parseSpecificDate(dateInput);
//            {
//                if (dateInput.contains("일 뒤") || dateInput.contains("일뒤")) {
//                    int days = Integer.parseInt(dateInput.replaceAll("[^0-9]", ""));
//                    yield today.plusDays(days).toString();
//                }
//                yield today.toString();
//            }
        };
    }

    private String parseSpecificDate(String dateInput){
        log.info("DateParserAgent: parseSpecificDate 호출");

        try{
            LocalDate date = LocalDate.parse(dateInput, INPUT_DATE_FORMATTER);
            return date.format(API_DATE_FORMATTER);
        }catch( DateTimeException e){
            throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. (YYYY-MM-DD or '내일', '모레' 등");
        }
    }

}
