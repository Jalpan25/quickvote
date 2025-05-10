package com.example.QuickVote.service;

import com.example.QuickVote.model.Option;
import com.example.QuickVote.model.Question;
import com.example.QuickVote.model.Response;
import com.example.QuickVote.model.Survey;
import com.example.QuickVote.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResponseService {
    @Autowired
    private ResponseRepository responseRepository;

    public void saveResponses(Long surveyId, String email, List<Map<String, Object>> responses) {
        List<Response> responseEntities = responses.stream().map(response -> {
            Response entity = new Response();
            entity.setSurvey(new Survey(surveyId));
            entity.setQuestion(new Question(((Number) response.get("questionId")).longValue()));
            entity.setOption(new Option(((Number) response.get("optionId")).longValue()));
            entity.setEmail(email); // Store email in Response entity
            return entity;
        }).collect(Collectors.toList());

        // Save all responses to the database
        responseRepository.saveAll(responseEntities);
    }

    public List<Map<String, String>> getResponses(Long surveyId, String email) {
        List<Response> responses = responseRepository.findBySurveyIdAndEmail(surveyId, email);

        return responses.stream().map(response -> Map.of(
                "questionId", response.getQuestion().getId().toString(),
                "selectedOptionId", response.getOption().getId().toString()
        )).collect(Collectors.toList());
    }
}
