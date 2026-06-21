package com.nhnacademy.flyschedule.controller;


import com.nhnacademy.flyschedule.service.agent.util.AirportCodeAgent;
import com.nhnacademy.flyschedule.service.agent.util.DateParserAgent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent")
public class AgentTestController {

    // ① 생성자 주입으로 에이전트를 주입받습니다.
    private final DateParserAgent dateParser;
    private final AirportCodeAgent airportAgent;

    public AgentTestController(DateParserAgent dateParser, AirportCodeAgent airportAgent) {
        this.dateParser = dateParser;
        this.airportAgent = airportAgent;
    }

    /**
     * 날짜 파싱 테스트
     * GET /api/agent/date-parse?input=내일
     */
    @GetMapping("/date-parse")
    public String testDateParser(@RequestParam String input) {
        try {
            // ② DateParserAgent만 단독으로 테스트
            String result = dateParser.parseDate(input);
            return "날짜 파싱 결과: " + input + " → " + result;
        } catch (Exception e) {
            return "파싱 실패: " + e.getMessage();
        }
    }

    /**
     * 공항 코드 변환 테스트
     * GET /api/agent/airport-code?input=광주
     */
    @GetMapping("/airport-code")
    public String testAirportCode(@RequestParam String input) {
        try {
            // ③ AirportCodeAgent만 단독으로 테스트
            String result = airportAgent.getAirportCode(input);
            return "공항 코드 변환: " + input + " → " + result;
        } catch (Exception e) {
            return "변환 실패: " + e.getMessage();
        }
    }

    /**
     * 에이전트 체이닝 테스트
     * GET /api/agent/chain?departure=광주&arrival=제주&date=내일
     */
    @GetMapping("/chain")
    public String testAgentChaining(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam String date) {

        try {
            // ④ 두 에이전트를 순차적으로 호출 (체이닝)
            //    AirportCodeAgent → AirportCodeAgent → DateParserAgent 순서
            String depCode = airportAgent.getAirportCode(departure);
            String arrCode = airportAgent.getAirportCode(arrival);
            String formattedDate = dateParser.parseDate(date);

            return String.format(
                    "에이전트 체이닝 결과:\n" +
                            "  출발: %s → %s\n" +
                            "  도착: %s → %s\n" +
                            "  날짜: %s → %s",
                    departure, depCode,
                    arrival, arrCode,
                    date, formattedDate
            );
        } catch (Exception e) {
            return "처리 실패: " + e.getMessage();
        }
    }
}