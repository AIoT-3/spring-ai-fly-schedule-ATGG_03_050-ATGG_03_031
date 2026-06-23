package com.nhnacademy.flyschedule.service.agent.util;

import com.nhnacademy.flyschedule.dto.Flight.FlightInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GroupingAgent {
    public Map<String, List<FlightInfoResponse>> groupByAirline(List<FlightInfoResponse> flightInfoResponseList){
        log.info("GroupingAgent: groupByAirline 호출");

        if(flightInfoResponseList == null || flightInfoResponseList.isEmpty()){
            return new TreeMap<>();
        }

        Map<String, List<FlightInfoResponse>> group = flightInfoResponseList.stream()
                .collect(Collectors.groupingBy(
                        flightInfoResponse -> {
                            String airlineName = flightInfoResponse.getAirlineNm();
                            if(airlineName == null || airlineName.trim().isEmpty()){
                                String flightId = flightInfoResponse.getVihicleId();
                                if(flightId == null || flightId.length() >= 2){
                                    return  flightId.substring(0,2);
                                }
                                return "알 수 없는 항공사";
                            }
                            return airlineName;
                        },
                        TreeMap::new,
                        Collectors.toList()
                ));
        log.info("그룹핑 완료: {}개 항공사, {}개 항공편", group.size(), flightInfoResponseList.size());
        return group;
    }
}