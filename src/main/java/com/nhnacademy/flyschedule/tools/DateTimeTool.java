package com.nhnacademy.flyschedule.tools;

import com.nhnacademy.flyschedule.service.agent.util.DateParserAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DateTimeTool implements MyAiTool{
    private final DateParserAgent dateParserAgent;

    /**
     * "내일", "모레" 같은 상대적 날짜를 실제 날짜로 변환
     */
    @Tool(description = """
            상대적 날짜를 실제 날짜(YYYY-MM-DD)로 변환합니다.
            '내일', '모레', '5일 뒤' 등을 지원합니다.
            """)
    public String parseDate(
            @ToolParam(description = "상대적 날짜 표현 (예: 내일, 모레, 3일 뒤)") String relativeDate) {
        log.info("parseDate tool 호출");
        return dateParserAgent.parseDate(relativeDate);
    }
}
