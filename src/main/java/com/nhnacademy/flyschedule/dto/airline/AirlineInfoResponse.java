package com.nhnacademy.flyschedule.dto.airline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineInfoResponse {
    @JsonProperty("airlineNm")
    String airlineNm; //공항명
    @JsonProperty("airlineId")
    String airlineId;//공항ID
}
