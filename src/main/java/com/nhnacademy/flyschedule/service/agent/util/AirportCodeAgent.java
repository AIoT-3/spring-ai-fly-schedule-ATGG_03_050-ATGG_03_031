package com.nhnacademy.flyschedule.service.agent.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class AirportCodeAgent {

    public String getAirportCode(String airportName){
        log.info("AirportCodeAgent: getAirportCode 호출");

        AirportCode code = AirportCode.getAirportCode(airportName.replaceAll("\\s+",""));
        if (code == null) {
            throw new IllegalArgumentException("알 수 없는 공항: " + airportName);
        }

        return code.name();
    }

    public boolean isValidAirport(String airportName){
        log.info("AirportCodeAgent: isValidAirport 호출");
        return AirportCode.getAirportCode(airportName.replaceAll("\\s+","")) !=null;
    }
}
