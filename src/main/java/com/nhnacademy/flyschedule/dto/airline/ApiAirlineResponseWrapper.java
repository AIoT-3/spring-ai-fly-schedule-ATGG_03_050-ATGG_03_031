package com.nhnacademy.flyschedule.dto.airline;

import com.nhnacademy.flyschedule.dto.airprt.ApiAirportResponseWrapper;
import lombok.Data;

import java.util.List;

@Data
public class ApiAirlineResponseWrapper {

    Response response;
    @Data
    public static class Response{
        Header header;
        Body body;

        @Data
        public static class Header{
            String resultCode;//결과코드
            String resultMsg; // 결과메시지
        }

        @Data
        public static class Body{
            Items items;
            @Data
            public static class Items{
                List<AirlineInfoResponse> items;
            }
        }
    }

    public List<AirlineInfoResponse> getItems(){
        return this.response.body != null && this.response.body.items != null
                ? this.response.body.items.getItems() : null;
    }

}
