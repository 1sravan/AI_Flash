package org.springai.flash.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springai.flash.exception.ApiException;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
public class OpenAiService {

    @Value("${spring.ai.openai.api-key}")
    private String openaikey;

    @Value("${spring.ai.openai.chat.options.model}")
    private String openaimodel;

    @Value("${spring.ai.openai.chat.options.temperature}")
    private double openaitemp;

    @Value("${spring.ai.openai.chat.options.maxtokens}")
    private int openaimaxtokens;

    private OpenAiChatModel openaichatModel;

    @Autowired
    public void ChatController(OpenAiChatModel chatModel) {
        this.openaichatModel = chatModel;
    }

    public JsonNode getAnswer(String question) {
        if (question == null || question.trim().isEmpty()) {
            return buildErrorResponse(new ApiException(400, "The question cannot be empty.", "INVALID_QUESTION", null));
        }
        JsonNode response = null;
        try {
            OpenAiApi openAiApi = new OpenAiApi(openaikey);
            OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
                    .model(openaimodel)
                    .temperature(openaitemp)
                    .maxTokens(openaimaxtokens)
                    .build();

            openaichatModel = new OpenAiChatModel(openAiApi, openAiChatOptions);
            ChatResponse chatResponse = openaichatModel.call(new Prompt(question));

            if (chatResponse.getResults() != null && !chatResponse.getResults().isEmpty()) {
                String text = chatResponse.getResults().toString();
                response = buildJsonResponse(text);
            } else {
                response = buildErrorResponse(new ApiException(400, "No response from model.", "NO_RESPONSE", null));
            }

        } catch (WebClientResponseException e) {

            if (e.getStatusCode().is4xxClientError()) {
                if(e.getStatusCode().value() == 401) {
                        response = buildErrorResponse(new ApiException(401, "Invalid API Key. Please check your credentials.", "INVALID_API_KEY", e));
                if(e.getStatusCode().value() == 429)
                        response = buildErrorResponse(new ApiException(429, "Rate limit exceeded, please try again later.", "RATE_LIMIT_EXCEEDED", e));
                else 
                        response = buildErrorResponse(new ApiException(e.getStatusCode().value(), "Client error occurred", "CLIENT_ERROR", e));
                }
            } else {
                response = buildErrorResponse(new ApiException(e.getStatusCode().value(), "API request failed", "API_ERROR", e));
            }

        } catch (Exception e) {
            response = buildErrorResponse(new ApiException(500, "Internal server error", "SERVER_ERROR", e));
        }

        return response;
    }

    private JsonNode buildJsonResponse(String text) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.createObjectNode().put("text", text);
    }

    private JsonNode buildErrorResponse (ApiException e) {
        Map<String, Object> errorResponse = Map.of(
                "status", e.getHttpStatusCode(),
                "message", e.getMessage(),
                "errorCode", e.getErrorCode());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.valueToTree(errorResponse);
    }

}
