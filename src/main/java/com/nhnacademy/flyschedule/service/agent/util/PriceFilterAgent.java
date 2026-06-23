package com.nhnacademy.flyschedule.service.agent.util;

import com.nhnacademy.flyschedule.dto.Flight.FlightInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PriceFilterAgent {
    public List<FlightInfoResponse> filterByPriceRange(
        List<FlightInfoResponse> flightInfoResponseList,
        Integer minPrice,
        Integer maxPrice
    ){
        log.info("PriceFilterAgent: filterByPriceRange 호출");

        if(flightInfoResponseList == null || flightInfoResponseList.isEmpty()){
            return List.of();
        }

        return flightInfoResponseList.stream()
                .filter(flightInfoResponse -> {
                    Integer economyPrice = flightInfoResponse.getEconomyCharge();
                    Integer prestigePrice = flightInfoResponse.getPrestigeCharge();
                    if(economyPrice== null || economyPrice == 0 ) return false;
                    if(minPrice != null && economyPrice < minPrice)return false;
                    return maxPrice == null || economyPrice <= maxPrice;

                }).collect(Collectors.toList());
    }

    //최저가 항공편 조회
    public FlightInfoResponse findCheapest(List<FlightInfoResponse> flightInfoResponseList){
        log.info("PriceFilterAgent: findCheapest 호출");

        return flightInfoResponseList.stream()
                .filter(f -> f.getEconomyCharge() != null && f.getEconomyCharge() > 0)
                .min((f1, f2) -> f1.getEconomyCharge().compareTo(f2.getEconomyCharge()))
                .orElse(null);
    }

    //평균 가격 계산
    public double calculateAveragePrice(List<FlightInfoResponse> flights) {
        log.info("PriceFilterAgent: calculateAveragePrice 호출");

        return flights.stream()
                .filter(f -> f.getEconomyCharge() != null && f.getEconomyCharge() > 0)
                .mapToInt(FlightInfoResponse::getEconomyCharge)
                .average()
                .orElse(0.0);
    }
}
