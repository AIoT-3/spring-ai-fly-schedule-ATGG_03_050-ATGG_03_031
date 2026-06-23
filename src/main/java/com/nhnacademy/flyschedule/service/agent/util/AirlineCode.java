package com.nhnacademy.flyschedule.service.agent.util;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum AirlineCode{
    AAR("아시아나항공"),
    ABL("에어부산"),
    ASV("에어서울"),
    ESR("이스타항공"),
    FGW("플라이강원"),
    HGG("하이에어"),
    JJA("제주항공"),
    JNA("진에어"),
    KAL("대한항공"),
    TWB("티웨이항공")
    ;

    private String airlineNm;
    AirlineCode(String airlineNm){
        this.airlineNm = airlineNm;
    }

    private static final Map<String, AirlineCode> AIRLINE_CODE_MAP = new HashMap<>();

    static{
        for(AirlineCode airline: AirlineCode.values()){
            AIRLINE_CODE_MAP.put(airline.name(), airline);
            AIRLINE_CODE_MAP.put(airline.airlineNm, airline);
        }
    }

    public static AirlineCode getAirlineCode(String input){
        if(input == null || input.isBlank()){
            return null;
        }

        return AIRLINE_CODE_MAP.get(input);
    }
}