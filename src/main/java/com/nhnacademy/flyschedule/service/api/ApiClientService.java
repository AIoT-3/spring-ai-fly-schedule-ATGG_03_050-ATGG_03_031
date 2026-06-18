package com.nhnacademy.flyschedule.service.api;

import com.nhnacademy.flyschedule.config.DataGoKrApiProperties;
import com.nhnacademy.flyschedule.dto.Flight.ApiFlightResponseWrapper;
import com.nhnacademy.flyschedule.dto.Flight.FlightInfoResponse;
import com.nhnacademy.flyschedule.dto.airline.AirlineInfoResponse;
import com.nhnacademy.flyschedule.dto.airline.ApiAirlineResponseWrapper;
import com.nhnacademy.flyschedule.dto.airprt.AirportInfoResponse;
import com.nhnacademy.flyschedule.dto.airprt.ApiAirportResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ApiClientService {
    private final DataGoKrApiProperties properties;
    private final RestClient restClient;


    public ApiClientService(DataGoKrApiProperties properties){
        this.properties = properties;
        this.restClient = RestClient.create();
    }

    private String encodeParam(String param){
        try {
            return URLEncoder.encode(param, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.warn("파라미터 인코딩 실패: {}", param);
            return param;
        }
    }

    public List<FlightInfoResponse> getFlightSchedule(
            String depAirportId,
            String arrAirportId,
            String depPlandTime
    ){

        String url = properties.getUrl() + "/GetFlightOpratInfoList"
                + "?serviceKey=" + properties.getServiceKey()
                + "&pageNo=1"
                + "&numOfRows=100"
                + "&_type=json"
                + "&depAirportId=" + encodeParam(depAirportId)
                + "&arrAirportId=" + encodeParam(arrAirportId)
                + "&depPlandTime=" + encodeParam(depPlandTime);

        log.info("항공편 API 호출 URL: {}", url);


        ApiFlightResponseWrapper responseWrapper = restClient.get()
                .uri(url)
                .retrieve()//실제 네트워크 통신 발생(HTTP 요청을 실제로 전송)
                .body(ApiFlightResponseWrapper.class);


        log.info("responseWrapper: {}",responseWrapper);


        if(responseWrapper != null &&  "00".equals(responseWrapper.getResultCode())){
            return responseWrapper.getItems() != null? responseWrapper.getItems() : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    public List<AirportInfoResponse> getAirprtList(){
        String url = properties.getUrl() + "/GetArprtList"
                + "?serviceKey" + properties.getServiceKey()
                + "&_type=json";

        ApiAirportResponseWrapper responseWrapper = restClient.get()
                .uri(url)
                .retrieve()
                .body(ApiAirportResponseWrapper.class);


        if(responseWrapper != null){
            return responseWrapper.getItems() != null? responseWrapper.getItems() : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    public List<AirlineInfoResponse> getAirlineList(){
        String url =  properties.getUrl() + "/GetAirmanList"
                + "?serviceKey=" + properties.getServiceKey()
                + "&_type=json";

        ApiAirlineResponseWrapper responseWrapper = restClient.get()
                .uri(url)
                .retrieve()
                .body(ApiAirlineResponseWrapper.class);


        if(responseWrapper != null){
            return responseWrapper.getItems() != null? responseWrapper.getItems() : Collections.emptyList();
        }
        return Collections.emptyList();
    }
}
