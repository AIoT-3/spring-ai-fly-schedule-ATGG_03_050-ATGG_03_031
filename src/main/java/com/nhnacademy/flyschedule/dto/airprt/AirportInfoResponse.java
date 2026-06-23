package com.nhnacademy.flyschedule.dto.airprt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportInfoResponse {
    @JsonProperty("airportNm")
    String airportNm;//공항명
    @JsonProperty("airportId")
    String airportId; //공항ID
}
