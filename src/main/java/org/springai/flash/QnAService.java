package org.springai.flash;

import  org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class QnAService {

    @Value("${gemini.api.url}")
    private String geminiapiurl;
    @Value("${gemini.api.key}")
    private String geminiapikey;

    private final WebClient webClient;

    public QnAService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public String getAnswer(String question) {

        // Contructing the Request Payload as per GEMINI
        Map<String,Object>  requestbody = Map.of(
                "contents",new Object[] {
                        Map.of("parts",new Object[]{
                                Map.of("text",question)
                        })
                }
        );

        //Making API Call
        String response = webClient.post()
                .uri(geminiapiurl + geminiapikey)
                .header("Content-Type","application/json")
                .bodyValue(requestbody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }


}


