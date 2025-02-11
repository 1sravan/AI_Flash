package org.springai.flash;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/qa")
public class AIController {
     private final QnAService qnaService;

    public AIController(QnAService qnaService) {
        this.qnaService = qnaService;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askQuestion(@RequestBody Map<String,String> payload)
    {
        String question = payload.get("question");
        String answer = qnaService.getAnswer(question);
        return ResponseEntity.ok(answer);
    }
}
