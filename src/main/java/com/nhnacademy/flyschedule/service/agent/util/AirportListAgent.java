package com.nhnacademy.flyschedule.service.agent.util;

import com.nhnacademy.flyschedule.dto.airprt.AirportInfoResponse;
import com.nhnacademy.flyschedule.service.api.ApiClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirportListAgent {

    private final ApiClientService apiClientService;

    public List<AirportInfoResponse> getAirportList(){
        return apiClientService.getAirprtList();
    }
}
