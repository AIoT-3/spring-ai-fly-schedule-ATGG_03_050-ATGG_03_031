package com.nhnacademy.flyschedule.dto.Flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApiFlightResponseWrapper {
    @JsonProperty("response")
    Response response;

    @Data
    public static class Response{
        @JsonProperty("header")
        Header header;
        @JsonProperty("body")
        Body body;
    }

    @Data
    public static class Header{
        @JsonProperty("resultCode")
        private String resultCode;//결과 코드
        @JsonProperty("resultMsg")
        private String resultMsg;//결과 메시지
    }

    @Data
    public static class Body{
        @JsonProperty("items")
        private Items items;
        @JsonProperty("numOfRows")
        private Integer numOfRows;//한 페이지 경과 수
        @JsonProperty("pageNo")
        private Integer pageNo;//페이지 번호
        @JsonProperty("totalCount")
        private Integer totalCount;//데이터 총 개수
    }

    @Data
    public static class Items{
        @JsonProperty("item")
        private List<FlightInfoResponse> item;
    }

    public List<FlightInfoResponse> getItems(){
        return response != null && response.body != null && response.body.items != null
                ? response.body.items.getItem() : null;
    }

    public String getResultCode(){
        return response != null && response.header != null
                ? response.header.getResultCode() : null;
    }
}
