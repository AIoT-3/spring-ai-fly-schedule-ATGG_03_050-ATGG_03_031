package com.nhnacademy.flyschedule.tools.capture;


import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class ToolResultCapture {
    private static final ThreadLocal<Map<String, Object>> RESULT = new ThreadLocal<>();

    public static void capture(String toolName, Object result){
        if(RESULT.get()==null){
            RESULT.set(new HashMap<>());
        }
        RESULT.get().put(toolName, result);
    }
    public static Map<String, Object> getAndClear(){
        Map<String, Object> result = RESULT.get();
        RESULT.remove();
        return result != null? result : new HashMap<>();
    }
}
