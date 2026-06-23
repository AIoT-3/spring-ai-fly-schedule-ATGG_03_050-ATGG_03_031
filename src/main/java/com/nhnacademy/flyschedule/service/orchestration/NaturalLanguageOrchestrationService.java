package com.nhnacademy.flyschedule.service.orchestration;

import com.nhnacademy.flyschedule.dto.Flight.FlightInfoResponse;
import com.nhnacademy.flyschedule.service.agent.FlightSearchAgent;
import com.nhnacademy.flyschedule.service.agent.util.*;
import com.nhnacademy.flyschedule.service.ai.LlmAnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaturalLanguageOrchestrationService {
    private final LlmAnalysisService llmAnalysisService;

    private final FlightSearchAgent flightSearchAgent;
    private final DateParserAgent dateParserAgent;
    private final AirportCodeAgent airportCodeAgent;
    private final GroupingAgent groupingAgent;
    private final PriceFilterAgent priceFilterAgent;
    private final TimeFilterAgent timeFilterAgent;

    public OrchestrationResult orchestrateFilightSearch(String message){
        log.info("자연어 항공편 검색 오케스트레이션 시작: {}", message);

        //llm으로 파라미터 추출
        Map<String, Object> params = llmAnalysisService.extractFlightServiceParams(message);
        if(!params.containsKey("departure") || !params.containsKey("arrival")){
            return OrchestrationResult.error("출발 공항과 도착 공항을 명확히 입력해주세요");
        }

        //날짜 처리
        String dateStr = (String) params.getOrDefault("date", LocalDate.now().toString());
        String parsedDate = dateParserAgent.parseDate(dateStr);
        log.info("날짜: {} -> {}", dateStr, parsedDate);

        //공항 코드 변환
        String departure = (String) params.get("departure");
        String arrival = (String) params.get("arrival");
        String depAirportId = airportCodeAgent.getAirportCode(departure);
        String arrAirportId = airportCodeAgent.getAirportCode(arrival);
        log.info("공항 코드: {} -> {}, {} -> {}",
                departure, depAirportId, arrival, arrAirportId);

        //항공편 검색
        Map<String, List<FlightInfoResponse>> flightsMap =
                flightSearchAgent.searchAndGroupByAirline(depAirportId, arrAirportId, parsedDate);
        List<FlightInfoResponse> flights = flightsMap.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        log.info("검색된 항공편: {}편", flights.size());

        //시간 필터링
        if (params.containsKey("afterTime")) {
            String afterTime = (String) params.get("afterTime");
            LocalTime time = LocalTime.parse(afterTime);
            flights = timeFilterAgent.filterAfterTime(flights, time);
            log.info("{} 이후 필터링: {}편", afterTime, flights.size());
        }

        if(params.containsKey("beforeTime")){
            String beforeTime = (String) params.get("beforeTime");
            LocalTime time = LocalTime.parse(beforeTime);
            flights = timeFilterAgent.filterAfterTime(flights, time);
            log.info("{} 이전 필터링: {}편", beforeTime, flights.size());
        }

        //가격 필터링
        if (params.containsKey("minPrice") || params.containsKey("maxPrice")) {
            Integer minPrice = params.containsKey("minPrice") ?
                    Integer.parseInt((String) params.get("minPrice")) : null;
            Integer maxPrice = params.containsKey("maxPrice") ?
                    Integer.parseInt((String) params.get("maxPrice")) : null;
            flights = priceFilterAgent.filterByPriceRange(flights, minPrice, maxPrice);
            log.info("가격 필터링 ({}~{}): {}편", minPrice, maxPrice, flights.size());
        }

        //항공사별 그루핑
        Map<String, List<FlightInfoResponse>> groupedFlights =
                groupingAgent.groupByAirline(flights);
        log.info("그룹핑 완료: {}개 항공사", groupedFlights.size());

        Map<String, List<Map<String, Object>>> resultData = groupedFlights.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(this::convertToMap)
                                .collect(Collectors.toList())
                ));

        return OrchestrationResult.success(params, resultData);

    }
    private Map<String, Object> convertToMap(FlightInfoResponse flight) {

        return Map.of(
                "vihicleId", flight.getVihicleId(),
                "airlineNm", flight.getAirlineNm(),
                "depPlandTime", flight.getDepPlandTime(),
                "arrPlandTime", flight.getArrPlandTime(),
                "economyCharge", flight.getEconomyCharge(),
                "prestigeCharge", flight.getPrestigeCharge(),
                "depAirportNm", flight.getDepAirportNm(),
                "arrAirportNm", flight.getArrAirportNm()
        );
    }

    public record OrchestrationResult(
            boolean success,
            String message,
            Map<String, Object> extractedParams,
            Map<String, List<Map<String, Object>>> data
    ) {

        public static OrchestrationResult success(
                Map<String, Object> params,
                Map<String, List<Map<String, Object>>> data
        ) {
            return new OrchestrationResult(
                    true,
                    "항공편 검색 성공",
                    params,
                    data
            );
        }

        public static OrchestrationResult error(String message) {
            return new OrchestrationResult(
                    false,
                    message,
                    null,
                    null
            );
        }
    }
}
