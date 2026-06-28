package com.example.QuickVote.controller;

import com.example.QuickVote.service.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/responses")
public class ResponseController {

    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping("/fetch")
    public ResponseEntity<Map<String, Object>> getSurveyResponses(@RequestBody Map<String, String> request) {
        Long surveyId = Long.parseLong(request.get("surveyId"));
        String email = request.get("email");

        List<Map<String, String>> responses = responseService.getResponses(surveyId, email);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("responses", responses);
        return ResponseEntity.ok(responseBody);
    }
}
