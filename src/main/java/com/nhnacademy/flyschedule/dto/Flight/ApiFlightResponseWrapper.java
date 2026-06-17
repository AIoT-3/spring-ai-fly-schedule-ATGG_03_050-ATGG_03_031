package com.nhnacademy.flyschedule.dto.Flight;

import lombok.Data;

import java.util.List;

@Data
public class ApiFlightResponseWrapper {
    Response response;

    @Data
    public static class Response{
        Header header;
        Body body;
        @Data
        public static class Header{
            private String resultCode;//결과 코드
            private String resultMessage;//결과 메시지
        }

        @Data
        public static class Body{
            private Items items;
            private Integer numOfRows;//한 페이지 경과 수
            private Integer pageNo;//페이지 번호
            private Integer totalCount;//데이터 총 개수
            @Data
            public static class Items{
                private List<FlightInfoResponse> items;
            }
        }


    }


    public List<FlightInfoResponse> getItems(){
        return response != null && response.body != null && response.body.items != null
                ? response.body.items.getItems() : null;
    }
}
