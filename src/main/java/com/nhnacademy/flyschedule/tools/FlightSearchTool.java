package com.nhnacademy.flyschedule.tools;

import com.nhnacademy.flyschedule.dto.Flight.FlightInfoResponse;
import com.nhnacademy.flyschedule.service.agent.FlightSearchAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
        log.info("searchFlightsByAirline tool 호출");
        return flightSearchAgent.searchAndGroupByAirline(departure, arrival, relativeDate);
    }

    //시간 필터링 검색
    @Tool(
    description = """
        
    """)
    public List<FlightInfoResponse> searchFlightsAfterTime(

    ){
        log.info("searchFlightsAfterTime tool 호출");

        return List.of();
    }

    //가격 필터링 검색
    @Tool(
            description = """
    
    """)
    public List<FlightInfoResponse> searchFlightsByPriceRange(){
        log.info("searchFlightsByPriceRange tool 호출");

        return List.of();
    }

}
