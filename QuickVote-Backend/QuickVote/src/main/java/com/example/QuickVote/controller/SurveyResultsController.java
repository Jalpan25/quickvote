package com.example.QuickVote.controller;

import com.example.QuickVote.model.*;
import com.example.QuickVote.repository.ResponseRepository;
import com.example.QuickVote.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/survey-results")
public class SurveyResultsController {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @PostMapping("/results")
    public Map<String, Object> getSurveyResults(@RequestBody Map<String, Long> request) {
        Long surveyId = request.get("surveyId");
        Optional<Survey> surveyOpt = surveyRepository.findById(surveyId);

        if (surveyOpt.isEmpty()) {
            throw new RuntimeException("Survey not found");
        }

        Survey survey = surveyOpt.get();
        List<Response> responses = responseRepository.findBySurveyId(surveyId);

        Map<Long, Map<Long, Long>> questionOptionCount = new HashMap<>();

        for (Response response : responses) {
            Long questionId = response.getQuestion().getId();
            Long optionId = response.getOption().getId();
            questionOptionCount.putIfAbsent(questionId, new HashMap<>());
            questionOptionCount.get(questionId).put(optionId,
                    questionOptionCount.get(questionId).getOrDefault(optionId, 0L) + 1);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("surveyId", survey.getId());
        result.put("surveyTitle", survey.getTitle());
        result.put("participationNo", survey.getParticipationNo()); // 👈 includes participation_no
        result.put("totalResponses", responses.size());

        List<Map<String, Object>> questionsList = new ArrayList<>();

        for (Question question : survey.getQuestions()) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("questionId", question.getId());
            questionData.put("questionText", question.getText());

            List<Map<String, Object>> optionsList = new ArrayList<>();
            for (Option option : question.getOptions()) {
                Map<String, Object> optionData = new HashMap<>();
                optionData.put("optionId", option.getId());
                optionData.put("optionText", option.getOptionText());
                optionData.put("frequency", questionOptionCount
                        .getOrDefault(question.getId(), Collections.emptyMap())
                        .getOrDefault(option.getId(), 0L));
                optionsList.add(optionData);
            }

            questionData.put("options", optionsList);
            questionsList.add(questionData);
        }

        result.put("questions", questionsList);
        return result;
    }
}
