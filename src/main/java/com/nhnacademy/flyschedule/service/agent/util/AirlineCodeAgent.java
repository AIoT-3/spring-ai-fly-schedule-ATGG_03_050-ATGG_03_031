package com.nhnacademy.flyschedule.service.agent.util;

import com.nhnacademy.flyschedule.exception.AirlineNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AirlineCodeAgent {


    public AirlineCode getAirlineCode(String airlineNm){
        log.info("AirlineCodeAgent: getAirlineCode 호출");

        AirlineCode code = AirlineCode.getAirlineCode(airlineNm.replaceAll("\\s+",""));
        if(airlineNm == null){
            throw new IllegalArgumentException("알 수 없는 항공사: " + airlineNm);
        }
        return code;
    }
    public boolean isValidAirline(String airlineNm){
        log.info("AirlineCodeAgent: isValidAirline 호출");

        return AirlineCode.getAirlineCode(airlineNm.replaceAll("\\s+","")) != null;
    }
}
