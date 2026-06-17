package com.nhnacademy.flyschedule.tools;

import com.nhnacademy.flyschedule.dto.airline.AirlineInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AirlineInfoTool implements MyAiTool{

    //전체 항공사 목록
    @Tool(description = """
            대한민국 항공사 목록을 조회.
  
            반환값: 대한민국 항공사 목록(List<항공사>)
            """)
    public List<AirlineInfoResponse> getAirlineList(){
        log.info("getAirlineList tool 호출");

        return List.of();
    }

    //항공사 ID 조회
    @Tool(
            description = """
     항공사의 ID를 조회
     
     파라미터
     - airlineNm : 항공사 이름(예: 대한항공, 아시아나항공, 티웨이항공)
     
     반환값: airlineNm으로 넘겨준 항공사의 항공사ID(String)
    """)
    public String getAirlineId(
            @ToolParam(description = "항공사 이름(예: 대한항공, 아시아나항공, 티웨이항공)") String airlineNm
    ){
        log.info("getAirlineId tool 호출");

        return null;
    }
}
