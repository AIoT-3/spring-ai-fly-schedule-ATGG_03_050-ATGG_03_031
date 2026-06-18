package com.nhnacademy.flyschedule.tools;

import com.nhnacademy.flyschedule.dto.Flight.FlightInfoResponse;
import com.nhnacademy.flyschedule.service.agent.FlightSearchAgent;
import com.nhnacademy.flyschedule.tools.capture.ToolResultCapture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlightSearchTool implements MyAiTool {

//    @Value("${data-go-kr.api.service-key}")
//    private String serviceKey;

    private final FlightSearchAgent flightSearchAgent;
    //항공사별 그루핑 검색
    @Tool(
            description = """
    항공편을 검색하여 항공사별로 그룹핑하여 반환합니다.
    출발 공항, 도착 공항, 날짜를 받아 항공사별로 정리된 항공편 목록을 제공합니다.

    사용 예시:
    - "내일 광주에서 제주로 가는 항공편"
    - "모레 김포에서 부산으로 가는 스케줄"

    파라미터:
    - departure: 출발 공항 이름 (예: 광주, 김포, 제주)
    - arrival: 도착 공항 이름 (예: 제주, 김포, 부산)
    - date: 날짜 (예: 내일, 모레, 2026-03-10)

    반환값: 항공사별 항공편 목록 (Map<항공사명, List<항공편>>)
    빠른 응답을 위해 항공사별 최대 3편만 반환
    """
    )
    public Map<String, List<FlightInfoResponse>> searchFlightsByAirline(
            @ToolParam(description = "출발 공항 이름 (예: 광주, 김포,김포공항, 제주, 제주공항)") String departure,
            @ToolParam(description = "도착 공항 이름 (예: 제주, 김포, 김포공항, 부산)") String arrival,
            @ToolParam(description = "상대적 날짜 (예: 내일, 모레, 2026-03-10)") String relativeDate
    ) {
        // 구현
        log.info("searchFlightsByAirline tool 호출(departure={}, arrival={}, date={})",
                departure, arrival, relativeDate);

        Map<String, List<FlightInfoResponse>> allFlights = flightSearchAgent.searchAndGroupByAirline(departure, arrival, relativeDate);
        Map<String, List<FlightInfoResponse>> limitedFlights = new HashMap<>();
        allFlights.forEach((airline, flights) -> {
            if (flights.size() > 3) {
                limitedFlights.put(airline, flights.subList(0, 3));
            } else {
                limitedFlights.put(airline, flights);
            }
        });


        ToolResultCapture.capture("searchFlightsByAirline", limitedFlights);
        return limitedFlights;
    }

    //시간 필터링 검색
    @Tool(
    description = """
        특정 시간 이후로 출발하는 항공편을 항공사별로 그룹핑하여 반환한다.
        시간 조건을 만족하는 항공편만 필터링하여 제공한다.
        시간 형식은 '14:00', '오후 2시' 등을 지원한다.
        
        사용예시:
        - "내일  2시 이후 광주에서 김포공항으로 갈 수 있는비행기 알아봐줘"
        - "내일 모레 오후 16시에 광주에서 제주로 갈 수 있는 비행기 알아봐줘"
        
        
        파라미터:
        - departure: 출발 공항 이름 (예: 광주, 김포, 제주)
        - arrival: 도착 공항 이름 (예: 제주, 김포, 부산)
        - date: 날짜 (예: 내일, 모레, 2026-03-10)
        - inputTime: 항공편 출발시간의 기준 (예: 오후 2시, 14:00)
        
        반환값: 
        - 항공사별 항공편 목록 (Map<항공사명, List<항공편>>)
        - 빠른 응답을 위해 항공사별 최대 3편만 반환한다.
    """)
    public Map<String, List<FlightInfoResponse>> searchFlightsAfterTime(
            @ToolParam(description = "출발 공항 이름 (예: 광주, 김포,김포공항, 제주, 제주공항)") String departure,
            @ToolParam(description = "도착 공항 이름 (예: 제주, 김포, 김포공항, 부산)") String arrival,
            @ToolParam(description = "상대적 날짜 (예: 내일, 모레, 2026-03-10)") String relativeDate,
            @ToolParam(description = "항공편 출발시간의 기준 (예: 오후 2시, 14:00")String inputTime
    ){
        log.info("searchFlightsAfterTime tool 호출departure={}, arrival={}, date={}, inputTime={})",
        departure, arrival, relativeDate, inputTime);
        Map<String, List<FlightInfoResponse>> allFlights = flightSearchAgent.searchWithTimeFilter(departure, arrival, relativeDate, inputTime);

        Map<String, List<FlightInfoResponse>> limitedFlights = new HashMap<>();
        allFlights.forEach((airline, flights) -> {
            if (flights.size() > 3) {
                limitedFlights.put(airline, flights.subList(0, 3));
            } else {
                limitedFlights.put(airline, flights);
            }
        });

        return limitedFlights;
    }

    //가격 필터링 검색
    @Tool(
            description = """
        특정 가격대 범위 내의 항고편만 필터링하여 항공사별로 그룹핑한다.
        최소 가격과 최대 가격은 선택사항이다.
        사용예시:
        - "내일 광주에서 김포공항으로 200000원 이내로 갈수 있는 비행기 알아봐줘"
        - "내일 모래 광주에서 제주로 50000원 이상 200000 이내로 이용할 수 있는 비행기 알아봐줘"
        
        파라미터:
        - departure: 출발 공항 이름 (예: 광주, 김포, 제주)
        - arrival: 도착 공항 이름 (예: 제주, 김포, 부산)
        - date: 날짜 (예: 내일, 모레, 2026-03-10)
        - minPrice: 운임요금 최소 가격 (예: 200000)
        - maxPrice: 운임요금 최대 가격 (예: 700000)
        
        반환값: 
        - 항공사별 항공편 목록 (Map<항공사명, List<항공편>>)
        - 빠른 응답을 위해 항공사별 최대 3편만 반환
    """)
    public Map<String, List<FlightInfoResponse>> searchFlightsByPriceRange(
            @ToolParam(description = "출발 공항 이름 (예: 광주, 김포,김포공항, 제주, 제주공항)") String departure,
            @ToolParam(description = "도착 공항 이름 (예: 제주, 김포, 김포공항, 부산)") String arrival,
            @ToolParam(description = "상대적 날짜 (예: 내일, 모레, 2026-03-10)") String relativeDate,
            @ToolParam(description = "운임요금 최소 가격 (예: 20000)") Integer minPrice,
            @ToolParam(description = "운임요금 최대 가격 (예: 70000)")Integer maxPrice
    ){
        log.info("searchFlightsByPriceRange tool 호출 departure={}, arrival={}, date={}, minPrice={}, maxPrice={})",
                departure, arrival, relativeDate,minPrice,maxPrice);

        Map<String, List<FlightInfoResponse>> allFlights = flightSearchAgent.searchWithPriceFilter(
                departure,
                arrival,
                relativeDate,
                minPrice,
                maxPrice
        );

        Map<String, List<FlightInfoResponse>> limitedFlights = new HashMap<>();
        allFlights.forEach((airline, flights) -> {
            if (flights.size() > 3) {
                limitedFlights.put(airline, flights.subList(0, 3));
            } else {
                limitedFlights.put(airline, flights);
            }
        });

        return limitedFlights;
    }

}
