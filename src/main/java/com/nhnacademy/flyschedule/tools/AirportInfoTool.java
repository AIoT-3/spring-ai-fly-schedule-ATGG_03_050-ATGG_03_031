package com.nhnacademy.flyschedule.tools;

import com.nhnacademy.flyschedule.dto.airprt.AirportInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class AirportInfoTool implements MyAiTool {

    //전체 공항 목록
    @Tool(
    description =
    """
        대한민국 공항 목록을 반환
        반환값: List<공항>
    """)
    public List<AirportInfoResponse> getAirportList() {
        log.info("getAirportList tool 호출");

        return List.of();
    }

    //공항 코드 조회
    @Tool(description =
    """
        공항이름과 매핑되는 공항ID 반환
        
        파라미터:
        - airprtNm: 공항이름(ex. 인청공항, 광주공항, 김포공항)
        반환값: 공항ID(String)
    """)
    public String getAirportCode(
            @ToolParam(description = "공항이름(ex. 인청공항, 광주공항, 김포공항)")String airprtNm
    ){
        log.info("getAirportCode tool 호출");

        return null;
    }

}
