package com.nhnacademy.flyschedule.service.agent.util;

import com.nhnacademy.flyschedule.dto.airline.AirlineInfoResponse;
import com.nhnacademy.flyschedule.service.api.ApiClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirlineListAgent {
    private final ApiClientService apiClientService;

    public List<AirlineInfoResponse> getAirlineList(){
        return apiClientService.getAirlineList();
    }
}
