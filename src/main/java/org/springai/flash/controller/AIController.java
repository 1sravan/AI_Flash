package org.springai.flash.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springai.flash.service.OpenAiService;
import org.springai.flash.service.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/qa")
public class AIController {

    @Autowired
    private QnAService qnaService;

    @Autowired
    private OpenAiService openaiService;


    @PostMapping("/ask")
    public ResponseEntity<JsonNode> askQuestion(@RequestBody Map<String,String> payload)
    {
        String question = payload.get("question");
        JsonNode answer = qnaService.getAnswer(question);

        return ResponseEntity.ok(answer);

    }

    @PostMapping("/openask")
    public ResponseEntity<JsonNode> askOpen(@RequestBody Map<String,String> payload)
    {
        String question = payload.get("question");
        JsonNode answer = openaiService.getAnswer(question);

        return ResponseEntity.ok(answer);
    }

}
