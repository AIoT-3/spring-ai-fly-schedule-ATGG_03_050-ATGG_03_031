package com.nhnacademy.flyschedule.dto.airprt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportInfoResponse {
    String airportNm;//공항명
    String airportId; //공항ID
}
