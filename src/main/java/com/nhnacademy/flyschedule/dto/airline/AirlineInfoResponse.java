package com.nhnacademy.flyschedule.dto.airline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineInfoResponse {
    String airlineNm; //공항명
    String airlineId;//공항ID
}
