package com.nhnacademy.flyschedule.config;

import com.nhnacademy.flyschedule.tools.MyAiTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ChatClientConfig {

    @Bean
    @Primary
    public ChatClient.Builder ollamaChatClientBuilder(
            @Qualifier("ollamaChatModel") ChatModel ollamaChatModel,
            List<MyAiTool> tools
    ){
        return ChatClient.builder(ollamaChatModel)
                .defaultTools(tools.toArray(Object[]::new))
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()
                );
    }

    @Bean
    public ChatClient.Builder geminiChatClientBuilder(
            @Qualifier("googleGenAiChatModel") ChatModel geminiChatModel,
            List<MyAiTool> tools
    ){
        return ChatClient.builder(geminiChatModel)
                .defaultTools(tools.toArray(new Object[0]))
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()
                );
    }
}
