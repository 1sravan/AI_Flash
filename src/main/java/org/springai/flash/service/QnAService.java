package org.springai.flash.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springai.flash.exception.ApiException;
import  org.springframework.beans.factory.annotation.Value;
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

    public String response;

    public QnAService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public JsonNode getAnswer(String question)
    {
        // Contructing the Request Payload as per GEMINI API
        Map<String, Object> requestbody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", question)
                        })
                }
        );

        try {
                //Making an API Call
                response = webClient.post()
                            .uri(geminiapiurl + geminiapikey)
                            .header("Content-Type", "application/json")
                            .bodyValue(requestbody)
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();

        } catch (Exception e) {
            throw new ApiException(500, "Failed to make an API call: " + e.getMessage(), "API_CALL_FAILURE", e);
        }

        // Extracting the Text
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode TextNode = null;
        try {
            TextNode = objectMapper.readTree(response).path("candidates").get(0)
                                    .path("content").path("parts").get(0);

        } catch (JsonProcessingException e) {
            throw new ApiException(400, "Invalid JSON response format", "INVALID_JSON", e);
        } catch (Exception e) {
            return buildErrorResponse(
                    new ApiException(500, "Internal server error while processing response", "SERVER_ERROR", e)
            );
        }

        return TextNode;

    }

    // Building the Error Response
    private JsonNode buildErrorResponse(ApiException e)
    {
         Map<String, Object> errorResponse = Map.of(
                        "status", e.getHttpStatusCode(),
                        "message", e.getMessage(),
                        "errorCode", e.getErrorCode());
         ObjectMapper objectMapper = new ObjectMapper();
         return objectMapper.valueToTree(errorResponse);
    }

}


