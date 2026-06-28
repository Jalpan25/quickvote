//package com.example.QuickVote.service;
//
//public class SurveyServiceRenamed {
//}
package com.example.QuickVote.service;

import com.example.QuickVote.model.Survey;
import com.example.QuickVote.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyServiceRenamed {

    @Autowired
    private SurveyRepository surveyRepository;

    public Survey getSurveyById(Long surveyId) {
        return surveyRepository.findById(surveyId).orElse(null);
    }
}
