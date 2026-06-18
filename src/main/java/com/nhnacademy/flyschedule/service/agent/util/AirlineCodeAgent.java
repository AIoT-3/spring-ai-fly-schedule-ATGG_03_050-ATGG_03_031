package com.nhnacademy.flyschedule.service.agent.util;

import com.nhnacademy.flyschedule.exception.AirlineNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class AirlineCodeAgent {

    enum AirlineCode{
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

    }
    public String getAirlineCode(String airlineNm){
        if(airlineNm == null){
            throw new IllegalArgumentException("항공사 이름을 입력해주세요");
        }
        for(AirlineCode code: AirlineCode.values()){
            if(code.airlineNm.equals(airlineNm)){
                return code.name();
            }
        }

        throw new AirlineNotFoundException(airlineNm);
    }
    public boolean isValidAirline(String airlineNm){
        for(AirlineCode code: AirlineCode.values()){
            if(code.airlineNm.equals(airlineNm)){
                return true;
            }
        }
        return false;
    }
}
