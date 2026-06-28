//package com.example.QuickVote.dto;
//
//public class SurveyDTORenamed {
//}
package com.example.QuickVote.dto;

import java.util.List;

public class SurveyDTORenamed {
    private List<QuestionDTORenamed> questions;

    public List<QuestionDTORenamed> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTORenamed> questions) {
        this.questions = questions;
    }
}
