package com.nhnacademy.flyschedule.dto.Flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightInfoResponse {
    @JsonProperty("arrAirportNm")
    String arrAirportNm;//도착공항명

    @JsonProperty("vihicleId")
    String vihicleId;//항공편명
    @JsonProperty("airlineNm")
    String airlineNm;//항공사명
    @JsonProperty("depPlandTime")
    String depPlandTime; // 출발시간 (YYYYMMDDHHmm)
    @JsonProperty("arrPlandTime")
    String arrPlandTime; //도착시간
    @JsonProperty("economyCharge")
    Integer economyCharge; // 일반석 운임(단위 : 원)
    @JsonProperty("prestigeCharge")
    Integer prestigeCharge; //비즈니스석운임(단위: 원)
    @JsonProperty("depAirportNm")
    String depAirportNm;//출발공항명

}
