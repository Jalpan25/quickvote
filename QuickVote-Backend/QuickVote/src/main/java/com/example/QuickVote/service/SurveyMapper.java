//package com.example.QuickVote.service;
//
//public class SurveyMapper {
//}
package com.example.QuickVote.service;

import com.example.QuickVote.dto.QuestionDTO;
import com.example.QuickVote.dto.SurveyDTO;
import com.example.QuickVote.model.Option;
import com.example.QuickVote.model.Question;
import com.example.QuickVote.model.Survey;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyMapper {

    public Survey mapToSurveyEntity(SurveyDTO surveyDTO) {
        Survey survey = new Survey();
        survey.setEmailRestriction(surveyDTO.getEmailRestriction());
        survey.setEndTime(surveyDTO.getEndTime());
        survey.setTitle(surveyDTO.getTitle());
        survey.setQuestions(surveyDTO.getQuestions().stream().map(this::mapToQuestionEntity).collect(Collectors.toList()));
        return survey;
    }

    private Question mapToQuestionEntity(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setText(questionDTO.getText());
        List<Option> options = new ArrayList<>();
        for(String option : questionDTO.getOptions()) {
            Option optionEntity = new Option();
            optionEntity.setOptionText(option);
            options.add(optionEntity);
        }
        question.setOptions(options);
        return question;
    }
}
