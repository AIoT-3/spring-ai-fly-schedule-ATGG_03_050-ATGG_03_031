package com.nhnacademy.flyschedule.service.agent;

import com.nhnacademy.flyschedule.dto.Flight.FlightInfoResponse;
import com.nhnacademy.flyschedule.service.agent.util.*;
import com.nhnacademy.flyschedule.service.api.ApiClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//코디네이터 에이전트
@Service
@RequiredArgsConstructor
@Slf4j
public class FlightSearchAgent {
    private final ApiClientService apiClientService;

    private final DateParserAgent dateParserAgent;
    private final AirportCodeAgent airportCodeAgent;
    private final GroupingAgent groupingAgent;
    private final PriceFilterAgent priceFilterAgent;
    private final TimeFilterAgent timeFilterAgent;

    public Map<String, List<FlightInfoResponse>> searchAndGroupByAirline(
            String departure,//출발지
            String arrival,//도착지
            String relativeDate//상대적 날짜
    ){



        return Map.of();
    }

    public Map<String, List<FlightInfoResponse>> searchWithTimeFilter(

    ){
        return Map.of();
    }

    public Map<String, List<FlightInfoResponse>> searchWithPriceFilter(

    ){
        return Map.of();
    }
}
