package com.example.QuickVote.controller;

import com.example.QuickVote.dto.SurveyDTORenamed;
import com.example.QuickVote.model.Survey;
import com.example.QuickVote.service.SurveyMapperRenamed;
import com.example.QuickVote.service.SurveyServiceRenamed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys")
public class SurveyControllerRenamed {

    @Autowired
    private SurveyServiceRenamed surveyService;

    @Autowired
    private SurveyMapperRenamed surveyMapper;

    @GetMapping("/{id}/questions")
    public ResponseEntity<SurveyDTORenamed> getSurveyQuestions(@PathVariable Long id) {
        Survey survey = surveyService.getSurveyById(id);
        if (survey == null) {
            return ResponseEntity.notFound().build();
        }
        SurveyDTORenamed surveyDTO = surveyMapper.mapToDTO(survey);
        return ResponseEntity.ok(surveyDTO);
    }
}
