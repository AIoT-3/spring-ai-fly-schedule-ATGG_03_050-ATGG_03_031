package com.nhnacademy.flyschedule.service.agent.util;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AirportCodeAgent {
    private static final Map<String, String> AIRPORT_CODE_MAP = new HashMap<>();

    static {
        // 수도권
        AIRPORT_CODE_MAP.put("김포", "NAARKSS");
        AIRPORT_CODE_MAP.put("김포공항", "NAARKSS");
        AIRPORT_CODE_MAP.put("인천", "NAARKSI");
        AIRPORT_CODE_MAP.put("인천공항", "NAARKSI");

        // 부산/경남권
        AIRPORT_CODE_MAP.put("김해", "NAARKJB");
        AIRPORT_CODE_MAP.put("김해공항", "NAARKJB");
        AIRPORT_CODE_MAP.put("부산", "NAARKJB");
        AIRPORT_CODE_MAP.put("사천", "NAARKPS");
        AIRPORT_CODE_MAP.put("사천공항", "NAARKPS");
        AIRPORT_CODE_MAP.put("울산", "NAARKNU");
        AIRPORT_CODE_MAP.put("울산공항", "NAARKNU");

        // 호남권
        AIRPORT_CODE_MAP.put("광주", "NAARKJJ");
        AIRPORT_CODE_MAP.put("광주공항", "NAARKJJ");
        AIRPORT_CODE_MAP.put("여수", "NAARKJY");
        AIRPORT_CODE_MAP.put("여수공항", "NAARKJY");
        AIRPORT_CODE_MAP.put("무안", "NAARKJB");
        AIRPORT_CODE_MAP.put("무안공항", "NAARKJB");

        // 영남권
        AIRPORT_CODE_MAP.put("대구", "NAARKTN");
        AIRPORT_CODE_MAP.put("대구공항", "NAARKTN");
        AIRPORT_CODE_MAP.put("포항", "NAARKPK");
        AIRPORT_CODE_MAP.put("포항공항", "NAARKPK");

        // 충청/강원권
        AIRPORT_CODE_MAP.put("청주", "NAARKNJ");
        AIRPORT_CODE_MAP.put("청주공항", "NAARKNJ");
        AIRPORT_CODE_MAP.put("양양", "NAARKNY");
        AIRPORT_CODE_MAP.put("양양공항", "NAARKNY");

        // 제주권
        AIRPORT_CODE_MAP.put("제주", "NAARKPC");
        AIRPORT_CODE_MAP.put("제주공항", "NAARKPC");
    }
    public String getAirportCode(String airportName){
        if(airportName == null || airportName.isBlank()){
            throw new IllegalArgumentException("공항 이름을 입력해주세요");
        }
        String normalized = airportName.trim();

        if(normalized.matches("NAARK[A-Z]{2}")){
            return normalized;
        }

        String code = AIRPORT_CODE_MAP.get(normalized);
        if(code == null){
            throw new IllegalArgumentException("알 수 없는 공항" + airportName);
        }
        return code;
    }

    public boolean isValidAirport(String airportName){
        if(airportName == null || airportName.isBlank()){
            return false;
        }
        return AIRPORT_CODE_MAP.containsKey(airportName.trim());
    }
}
