package com.nhnacademy.flyschedule.service.agent;

import com.nhnacademy.flyschedule.dto.Flight.FlightInfoResponse;
import com.nhnacademy.flyschedule.service.agent.util.*;
import com.nhnacademy.flyschedule.service.api.ApiClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        log.info("FlightSearchAgent: searchAndGroupByAirline 호출");
        String parsedDate = dateParserAgent.parseDate(relativeDate);
        log.info("DateParserAgent: {} → {}", relativeDate, parsedDate);


        String depAirportId = airportCodeAgent.getAirportCode(departure);
        String arrAirportId = airportCodeAgent.getAirportCode(arrival);
        log.info("AirportCodeAgent: {} → {}, {} → {}", departure, depAirportId, arrival, arrAirportId);

        List<FlightInfoResponse> flightInfoResponseList = apiClientService.getFlightSchedule(depAirportId, arrAirportId, parsedDate);
        if(flightInfoResponseList.isEmpty()){
            return null;
        }

        return groupingAgent.groupByAirline(flightInfoResponseList);
    }

    public Map<String, List<FlightInfoResponse>> searchWithTimeFilter(
            String departure,//출발지
            String arrival,//도착지
            String relativeDate,//상대적 날짜
            String timeInput//출발시간
    ){
        log.info("FlightSearchAgent: searchWithTimeFilter 호출");

        Map<String, List<FlightInfoResponse>> flightsGroupByAirline = searchAndGroupByAirline(departure, arrival, relativeDate);

        LocalTime afterTime = timeFilterAgent.parseTime(timeInput);
        Map<String, List<FlightInfoResponse>> flightInfoResponseList = flightsGroupByAirline.entrySet().stream()
                .collect(
                        Collectors.toMap(
                            Map.Entry::getKey,
                                stringListEntry -> timeFilterAgent.filterAfterTime(
                                        stringListEntry.getValue(),afterTime
                                )
                        )
                );


        return flightInfoResponseList;
    }

    public Map<String, List<FlightInfoResponse>> searchWithPriceFilter(
            String departure,//출발지
            String arrival,//도착지
            String relativeDate,//상대적 날짜
            Integer minPrice,//최소금액
            Integer maxPrice//최대금액
    ){
        log.info("FlightSearchAgent: searchWithPriceFilter 호출");

        Map<String, List<FlightInfoResponse>> flightsGroupByAirline = searchAndGroupByAirline(departure, arrival, relativeDate);

        return flightsGroupByAirline.entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                stringListEntry -> priceFilterAgent.filterByPriceRange(
                                        stringListEntry.getValue(), minPrice, maxPrice
                                )
                        )
                );
    }
}
