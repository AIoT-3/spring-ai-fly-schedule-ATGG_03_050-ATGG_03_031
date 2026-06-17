package com.nhnacademy.flyschedule.dto.Flight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightInfoResponse {
    String arrAirportNm;//도착공항명
    String vihicleId;//항공편명
    String airlineNm;//항공사명
    String depPlandTime; // 출발시간 (YYYYMMDDHHmm)
    String arrPlandTime; //도착시간
    Integer economyCharge; // 일반석 운임(단위 : 원)
    Integer prestigeCharge; //비즈니스석운임(단위: 원)
    String depAirportNm;//출발공항명

}
