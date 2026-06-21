package com.nhnacademy.flyschedule.service.agent.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@Slf4j
@Service
public class DateParserAgent {
    //datago-api가 요구하는 날짜 형식
    private static final DateTimeFormatter API_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    //사용자가 입렬할 수 있는 날짜 형식
    private static final DateTimeFormatter INPUT_DATE_FORMATTER1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter INPUT_DATE_FORMATTER2 = new DateTimeFormatterBuilder()
            .appendPattern("[yyyy년][yy년]M월d일")
            .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
            .toFormatter();

    public String parseDate(String dateInput){
        log.info("DateParserAgent: parseDate 호출");

        if(dateInput == null || dateInput.isBlank()){
            return LocalDate.now().format(API_DATE_FORMATTER);
        }
        String normalized = dateInput.replaceAll("\\s+", "");

        if(normalized.replaceAll("[^0-9]","").matches("\\d{8}")){
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
        };
    }

    private String parseSpecificDate(String dateInput){
        log.info("DateParserAgent: parseSpecificDate 호출");
        LocalDate today = LocalDate.now();
        try{
            if (dateInput.matches("\\d+일(?:뒤|후)")) {
                int days = Integer.parseInt(dateInput.replaceAll("[^0-9]", ""));
                return today.plusDays(days).format(API_DATE_FORMATTER);
            }
            if(dateInput.contains("-")) {
                LocalDate date = LocalDate.parse(dateInput, INPUT_DATE_FORMATTER1);
                return date.format(API_DATE_FORMATTER);
            }
            if(dateInput.contains("월") && dateInput.contains("일")){
                LocalDate date = LocalDate.parse(dateInput,INPUT_DATE_FORMATTER2);
                return date.format(API_DATE_FORMATTER);
            }
        }catch( DateTimeException e){
            log.error("날짜 파싱 실패 - dateInput: {}", dateInput, e);
        }
        throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다. (YYYY-MM-DD or '내일', '모레' '0일 뒤' 등");
    }

}
