package com.nhnacademy.flyschedule.service.agent.util;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public enum AirportCode {

    NAARKJB("무안", Set.of("무안", "무안공항")),
    NAARKJJ("광주", Set.of("광주", "광주공항")),
    NAARKJK("군산", Set.of("군산", "군산공항")),
    NAARKJY("여수", Set.of("여수", "여수공항")),
    NAARKNW("원주", Set.of("원주", "원주공항")),
    NAARKNY("양양", Set.of("양양", "양양공항")),
    NAARKPC("제주", Set.of("제주", "제주공항")),
    NAARKPK("김해", Set.of("김해", "김해공항", "부산")),
    NAARKPS("사천", Set.of("사천", "사천공항")),
    NAARKPU("울산", Set.of("울산", "울산공항")),
    NAARKSI("인천", Set.of("인천", "인천공항")),
    NAARKSS("김포", Set.of("김포", "김포공항")),
    NAARKTH("포항", Set.of("포항", "포항공항")),
    NAARKTN("대구", Set.of("대구", "대구공항")),
    NAARKTU("청주", Set.of("청주", "청주공항"));

    private final String airportNm;
    private final Set<String> aliases;

    AirportCode(String airportNm, Set<String> aliases) {
        this.airportNm = airportNm;
        this.aliases = aliases;
    }

    private static final Map<String, AirportCode> AIRPORT_CODE_MAP = new HashMap<>();

    static {
        for (AirportCode airport : values()) {
            AIRPORT_CODE_MAP.put(airport.name(), airport);


            for (String alias : airport.aliases) {
                AIRPORT_CODE_MAP.put(alias, airport);
            }

        }
    }

    public static AirportCode getAirportCode(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        AirportCode result = AIRPORT_CODE_MAP.get(input.trim());

        return result;
    }

}
