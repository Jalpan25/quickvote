package com.example.QuickVote.service;

import com.example.QuickVote.dto.QuestionDTORenamed;
import com.example.QuickVote.dto.SurveyDTORenamed;
import com.example.QuickVote.model.Survey;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SurveyMapperRenamed {
    public SurveyDTORenamed mapToDTO(Survey survey) {
        SurveyDTORenamed surveyDTO = new SurveyDTORenamed();
        surveyDTO.setQuestions(survey.getQuestions().stream().map(question -> {
            QuestionDTORenamed questionDTO = new QuestionDTORenamed();
            questionDTO.setId(question.getId());
            questionDTO.setText(question.getText());
            questionDTO.setOptions(question.getOptions());
            return questionDTO;
        }).collect(Collectors.toList()));
        return surveyDTO;
    }
}
